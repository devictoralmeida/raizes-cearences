package com.devictoralmeida.teste.services.impl;

import com.devictoralmeida.teste.dto.request.AnexoRequestDto;
import com.devictoralmeida.teste.dto.request.ContatoRequestDto;
import com.devictoralmeida.teste.dto.request.SenhaRequestDto;
import com.devictoralmeida.teste.dto.request.UsuarioRequestDto;
import com.devictoralmeida.teste.dto.request.update.ContatoUpdateRequestDto;
import com.devictoralmeida.teste.entities.*;
import com.devictoralmeida.teste.enums.TipoCodigoVerificacao;
import com.devictoralmeida.teste.enums.TipoContato;
import com.devictoralmeida.teste.enums.TipoPerfil;
import com.devictoralmeida.teste.factories.DadosPessoaPerfilTermoRepository;
import com.devictoralmeida.teste.repositories.UsuarioRepository;
import com.devictoralmeida.teste.services.*;
import com.devictoralmeida.teste.services.rules.RegraPessoaPerfilAnexo;
import com.devictoralmeida.teste.shared.auditoria.CustomRevisionListener;
import com.devictoralmeida.teste.shared.constants.errors.ContatoErrorsMessageConstants;
import com.devictoralmeida.teste.shared.constants.errors.FirebaseErrorsMessageConstants;
import com.devictoralmeida.teste.shared.constants.errors.UsuarioErrorsMessageConstants;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import com.devictoralmeida.teste.shared.exceptions.RecursoNaoEncontradoException;
import com.devictoralmeida.teste.shared.utils.FileUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
  private final UsuarioRepository usuarioRepository;
  private final FileService fileService;
  private final CodigoVerificacaoService codigoVerificacaoService;
  private final FirebaseService firebaseService;
  private final EmailService emailService;
  private final MensagemService mensagemService;
  private final DadosPessoaPerfilTermoRepository dadosPessoaPerfilTermoRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  @Override
  public void save(UsuarioRequestDto request) {
    request.validar();
    validarDocumentoExistente(request);
    validarContatoExistente(request.getPessoaPerfil().getContato());

    TermoCondicao termoCondicao = dadosPessoaPerfilTermoRepository.getTermoCondicaoRepository().findLatest();
    Usuario usuario = new Usuario(request, termoCondicao);
    PessoaPerfil pessoaPerfil = createPessoaPerfil(request, usuario);
    CodigoVerificacao codigoVerificacao = codigoVerificacaoService.save(TipoCodigoVerificacao.CONTATO, usuario);
    usuario.setPessoaPerfil(pessoaPerfil);
    usuario.setCodigoVerificacao(codigoVerificacao);

    try {
      UserRecord usuarioFirebase = firebaseService.criarUsuarioFirebase(request);
      usuario.setFirebaseUID(usuarioFirebase.getUid());
      firebaseService.adicionarPermissoesTokenFirebase(usuarioFirebase.getUid(), usuario.getAuthorities());
      enviarCodigo(usuario, codigoVerificacao, usuario.getPessoaPerfil().getContato().getPreferenciaContato());
      usuarioRepository.save(usuario);
    } catch (FirebaseAuthException e) {
      throw new NegocioException(FirebaseErrorsMessageConstants.ERRO_CRIAR_USUARIO);
    }
  }

  @Override
  public Usuario findById(UUID id) {
    return usuarioRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException(UsuarioErrorsMessageConstants.USUARIO_NAO_ENCONTRADO));
  }

  @Override
  public Usuario findByLogin(String login) {
    return usuarioRepository.findByLogin(login).orElseThrow(() -> new RecursoNaoEncontradoException(UsuarioErrorsMessageConstants.USUARIO_NAO_ENCONTRADO));
  }

  @Override
  public Usuario findByFirebaseUID(String uid) {
    return usuarioRepository.findByFirebaseUID(uid).orElseThrow(() -> new RecursoNaoEncontradoException(UsuarioErrorsMessageConstants.USUARIO_NAO_ENCONTRADO));
  }

  @Transactional
  @Override
  public void validarCodigo(String login, String codigo) {
    Usuario usuario = findByLogin(login);
    codigoVerificacaoService.validarCodigoConfirmacao(usuario, codigo);

    if (TipoContato.EMAIL.equals(usuario.getPessoaPerfil().getContato().getPreferenciaContato())) {
      firebaseService.emailVerificado(usuario.getFirebaseUID());
    }

    usuarioRepository.save(usuario);
  }

  @Transactional
  @Override
  public void reenviarCodigo(String login) {
    Usuario usuario = findByLogin(login);
    TipoContato canalEnvio = usuario.getPessoaPerfil().getContato().getPreferenciaContato();
    CodigoVerificacao codigoVerificacao = codigoVerificacaoService.save(TipoCodigoVerificacao.CONTATO, usuario);
    usuario.setCodigoVerificacao(codigoVerificacao);
    usuarioRepository.save(usuario);
    enviarCodigo(usuario, codigoVerificacao, canalEnvio);
  }

  @Transactional
  @Override
  public void uploadAnexos(String login, @Valid List<AnexoRequestDto> anexos) {
    Usuario usuario = findByLogin(login);
    RegraPessoaPerfilAnexo.validar(anexos, usuario.getTipoPerfil());

    anexos.forEach(anexo -> {
      fileService.upload(anexo.getArquivo());
      anexo.setNome(FileUtils.removerExtensao(anexo.getArquivo().getOriginalFilename()));
      PessoaPerfilAnexo pessoaPerfilAnexo = new PessoaPerfilAnexo(anexo, usuario);
      usuario.getPessoaPerfil().getAnexos().add(pessoaPerfilAnexo);
      dadosPessoaPerfilTermoRepository.getPessoaPerfilAnexoRepository().save(pessoaPerfilAnexo);
    });
  }

  @Transactional
  @Override
  public void alterarContato(String login, ContatoUpdateRequestDto request) throws JsonProcessingException {
    request.validar();
    Usuario usuario = findByLogin(login);
    Contato contato = usuario.getPessoaPerfil().getContato();

    boolean houveMudanca = contato.validarMudancaUpdateCodigo(request);

    if (houveMudanca) {
      if (Objects.nonNull(contato.getNumeroWhatsapp()) && !contato.getNumeroWhatsapp().equals(request.getNumeroWhatsapp())) {
        verificarWhatsappExistente(request.getNumeroWhatsapp());
      }

      if (Objects.nonNull(contato.getEmail()) && !contato.getEmail().equals(request.getEmail())) {
        verificarEmailExistente(request.getEmail());
      }

      CustomRevisionListener.setDadosAntigos(usuario.toStringMapper());
      contato.aplicarMudancaUpdateCodigo(request);
    }

    CodigoVerificacao codigoVerificacao = codigoVerificacaoService.save(TipoCodigoVerificacao.CONTATO, usuario);
    usuario.setCodigoVerificacao(codigoVerificacao);

    try {
      Usuario updatedUser = usuarioRepository.save(usuario);
      boolean usuarioPossuiEmail = Objects.nonNull(updatedUser.getPessoaPerfil().getContato().getEmail());
      firebaseService.atualizarContatoUsuarioFirebase(updatedUser.getFirebaseUID(), request, usuarioPossuiEmail);
      enviarCodigo(usuario, codigoVerificacao, updatedUser.getPessoaPerfil().getContato().getPreferenciaContato());
    } catch (FirebaseAuthException e) {
      throw new NegocioException(FirebaseErrorsMessageConstants.ERRO_ATUALIZAR_CONTATO_USUARIO);
    }
  }

  @Transactional
  @Override
  public void criarSenha(String login, SenhaRequestDto request) {
    request.validar();
    Usuario usuario = findByLogin(login);
    verificarValidacaoCodigo(usuario);
    alterarSenha(usuario, request.getSenha());
  }

  @Override
  public void alterarSenha(Usuario usuario, String senha) {
    try {
      usuario.setSenha(passwordEncoder.encode(senha));
      firebaseService.atualizarSenhaUsuarioFirebase(usuario.getFirebaseUID(), senha);
      usuarioRepository.save(usuario);
    } catch (FirebaseAuthException e) {
      throw new NegocioException(FirebaseErrorsMessageConstants.ERRO_ATUALIZAR_SENHA_USUARIO);
    }
  }

  @Override
  public void verificarValidacaoCodigo(Usuario usuario) {
    if (TipoCodigoVerificacao.CONTATO.equals(usuario.getCodigoVerificacao().getTipoCodigo()) && !usuario.getCodigoVerificacao().isValido()) {
      throw new NegocioException(UsuarioErrorsMessageConstants.CODIGO_VALIDACAO_CONTATO_NAO_REALIZADO);
    } else if (TipoCodigoVerificacao.SENHA.equals(usuario.getCodigoVerificacao().getTipoCodigo()) && !usuario.getCodigoVerificacao().isValido()) {
      throw new NegocioException(UsuarioErrorsMessageConstants.CODIGO_VALIDACAO_SENHA_NAO_REALIZADO);
    }
  }

  @Override
  public void verificarAceiteTermos(Usuario usuario) {
//    if (usuario.getDataAceiteTermos() == null) {
//      throw new NegocioException(UsuarioErrorsMessageConstants.ACEITE_TERMOS_NAO_REALIZADO);
//    }
  }

  @Transactional
  @Override
  public void enviarCodigoRecuperacaoSenha(Usuario usuario) {
    CodigoVerificacao codigoVerificacao = codigoVerificacaoService.save(TipoCodigoVerificacao.SENHA, usuario);
    usuario.setCodigoVerificacao(codigoVerificacao);
    usuarioRepository.save(usuario);
    enviarCodigo(usuario, codigoVerificacao, usuario.getPessoaPerfil().getContato().getPreferenciaContato());
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public void delete(UUID id) {
    Usuario usuario = findById(id);

    try {
      firebaseService.deletarUsuarioFirebase(usuario.getFirebaseUID());
      usuarioRepository.delete(usuario);
    } catch (FirebaseAuthException e) {
      throw new NegocioException(FirebaseErrorsMessageConstants.ERRO_DELETAR_USUARIO);
    }
  }

  private void enviarCodigo(Usuario usuario, CodigoVerificacao codigoVerificacao, TipoContato preferenciaContato) {
    if (TipoContato.EMAIL.equals(preferenciaContato)) {
//      emailService.enviarEmail(usuario.getPessoaPerfil().getContato().getEmail(), codigoVerificacao.getCodigo());
    } else if (TipoContato.WHATSAPP.equals(preferenciaContato)) {
      mensagemService.enviarWhatsapp(usuario.getPessoaPerfil().getContato().getNumeroWhatsapp(), codigoVerificacao.getCodigo());
    }
  }

  private void validarDocumentoExistente(UsuarioRequestDto request) {
    if (usuarioRepository.findByLogin(request.getLogin()).isPresent()) {
      throw new NegocioException(UsuarioErrorsMessageConstants.DOCUMENTO_EXISTENTE);
    }
  }

  private void validarContatoExistente(ContatoRequestDto contato) {
    if (Objects.nonNull(contato.getEmail())) {
      verificarEmailExistente(contato.getEmail());
    }

    if (Objects.nonNull(contato.getNumeroWhatsapp())) {
      verificarWhatsappExistente(contato.getNumeroWhatsapp());
    }
  }

  private void verificarWhatsappExistente(String numeroWhatsapp) {
    if (usuarioRepository.findByWhatsapp(numeroWhatsapp).isPresent()) {
      throw new NegocioException(ContatoErrorsMessageConstants.WHATSAPP_JA_CADASTRADO);
    }
  }

  private void verificarEmailExistente(String email) {
    if (usuarioRepository.findByEmail(email).isPresent()) {
      throw new NegocioException(ContatoErrorsMessageConstants.EMAIL_JA_CADASTRADO);
    }
  }

  private PessoaPerfil createPessoaPerfil(UsuarioRequestDto request, Usuario usuario) {
    if (request.getPessoaPerfil().getDadosPessoaFisica() != null) {
      return createPessoaPerfilFisica(request, usuario);
    } else {
      return createPessoaPerfilJuridica(request, usuario);
    }
  }

  private PessoaPerfil createPessoaPerfilFisica(UsuarioRequestDto request, Usuario usuario) {
    DadosPessoaFisica dadosPessoaFisica = new DadosPessoaFisica(request.getPessoaPerfil().getDadosPessoaFisica());
    dadosPessoaPerfilTermoRepository.getDadosPessoaFisicaRepository().save(dadosPessoaFisica);
    return new PessoaPerfil(request.getPessoaPerfil(), usuario, dadosPessoaFisica.getId());
  }

  private PessoaPerfil createPessoaPerfilJuridica(UsuarioRequestDto request, Usuario usuario) {
    DadosPessoaJuridica dadosPessoaJuridica = new DadosPessoaJuridica(request.getPessoaPerfil().getDadosPessoaJuridica());
    dadosPessoaPerfilTermoRepository.getDadosPessoaJuridicaRepository().save(dadosPessoaJuridica);

    if (isCooperativaAssociacao(usuario)) {
      return createPessoaPerfilComPresidente(request, usuario, dadosPessoaJuridica);
    } else {
      return new PessoaPerfil(request.getPessoaPerfil(), usuario, dadosPessoaJuridica.getId());
    }
  }

  private PessoaPerfil createPessoaPerfilComPresidente(UsuarioRequestDto request, Usuario usuario, DadosPessoaJuridica dadosPessoaJuridica) {
    DadosPessoaFisica dadosPresidente = new DadosPessoaFisica(request.getPessoaPerfil().getPresidente().getDadosPessoais());
    dadosPessoaPerfilTermoRepository.getDadosPessoaFisicaRepository().save(dadosPresidente);
    return new PessoaPerfil(request.getPessoaPerfil(), usuario, dadosPessoaJuridica.getId(), dadosPresidente.getId());
  }

  private boolean isCooperativaAssociacao(Usuario usuario) {
    return TipoPerfil.COOPERATIVA.equals(usuario.getTipoPerfil()) || TipoPerfil.ASSOCIACAO.equals(usuario.getTipoPerfil());
  }
}