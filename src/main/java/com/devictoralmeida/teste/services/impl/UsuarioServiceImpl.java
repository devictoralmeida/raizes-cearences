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
import com.devictoralmeida.teste.enums.TipoUsuario;
import com.devictoralmeida.teste.factories.DadosPessoaPerfilTermoRepository;
import com.devictoralmeida.teste.helpers.AnexoRequestHelper;
import com.devictoralmeida.teste.helpers.ContatoRequestHelper;
import com.devictoralmeida.teste.helpers.UsuarioRequestHelper;
import com.devictoralmeida.teste.repositories.UsuarioRepository;
import com.devictoralmeida.teste.services.*;
import com.devictoralmeida.teste.shared.auditoria.CustomRevisionListener;
import com.devictoralmeida.teste.shared.constants.errors.ContatoErrorsMessageConstants;
import com.devictoralmeida.teste.shared.constants.errors.FirebaseErrorsMessageConstants;
import com.devictoralmeida.teste.shared.constants.errors.UsuarioErrorsMessageConstants;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import com.devictoralmeida.teste.shared.exceptions.RecursoNaoEncontradoException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
  private final UsuarioRepository usuarioRepository;
  private final BucketService bucketService;
  private final CodigoVerificacaoService codigoVerificacaoService;
  private final FirebaseService firebaseService;
  private final EmailService emailService;
  private final MensagemService mensagemService;
  private final DadosPessoaPerfilTermoRepository dadosPessoaPerfilTermoRepository;

  @Transactional
  @Override
  public void save(UsuarioRequestDto request) {
    UsuarioRequestHelper.validar(request);
    validarDocumentoExistente(request);
    validarContatoExistente(request.getPessoaPerfil().getContato());

    TermoCondicao termoCondicao = dadosPessoaPerfilTermoRepository.getTermoCondicaoRepository().findLatest();
    Usuario usuario = new Usuario(request, termoCondicao);
    PessoaPerfil pessoaPerfil = createPessoaPerfil(request, usuario);
    CodigoVerificacao codigoVerificacao = codigoVerificacaoService.save(TipoCodigoVerificacao.CONTATO, usuario);
    codigoVerificacao.setValido(true);
    usuario.setPessoaPerfil(pessoaPerfil);
    usuario.setCodigoVerificacao(codigoVerificacao);

    if (!TipoPerfil.CONSUMIDOR.equals(usuario.getTipoPerfil())) {
      Set<PerfilAcesso> perfilAcessos = new HashSet<>(dadosPessoaPerfilTermoRepository.getPerfilAcessoRepository().getPerfilAcessoOfertasPlanos());
      usuario.setPerfisAcessos(perfilAcessos);
    }

    try {
      UserRecord usuarioFirebase = firebaseService.criarUsuarioFirebase(request);
      usuario.setFirebaseUID(usuarioFirebase.getUid());
      firebaseService.adicionarPermissoesTokenFirebase(usuarioFirebase.getUid(), usuario.getAuthorities());
//      enviarCodigo(usuario, codigoVerificacao, usuario.getPessoaPerfil().getContato().getPreferenciaContato());
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
  public Usuario getUsuarioLogadoByLogin(String login) {
    return usuarioRepository.getUsuarioLogadoByLogin(login);
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
    AnexoRequestHelper.validarLista(anexos, usuario.getTipoPerfil());

    anexos.forEach(anexoRequest -> {
      anexoRequest.setNome(anexoRequest.getAnexo().getOriginalFilename());
      Anexo anexoEntity = new Anexo(anexoRequest);
      dadosPessoaPerfilTermoRepository.getAnexoRepository().save(anexoEntity);
      bucketService.upload(anexoRequest, anexoEntity.getId());

      PessoaPerfilAnexo pessoaPerfilAnexo = new PessoaPerfilAnexo(anexoRequest, usuario);
      pessoaPerfilAnexo.setAnexo(anexoEntity);
      usuario.getPessoaPerfil().getPessoaPerfilAnexo().add(pessoaPerfilAnexo);
    });
    usuarioRepository.save(usuario);
  }

  @Transactional
  @Override
  public void alterarContato(String login, ContatoUpdateRequestDto request) throws JsonProcessingException {
    ContatoRequestHelper.validar(request);
    Usuario usuario = findByLogin(login);
    Contato contato = usuario.getPessoaPerfil().getContato();
    boolean houveMudanca = contato.validarMudancaUpdateCodigo(request);

    if (houveMudanca) {
      if (contato.getNumeroWhatsapp() != null && !contato.getNumeroWhatsapp().equals(request.getNumeroWhatsapp())) {
        verificarWhatsappExistente(request.getNumeroWhatsapp());
      }

      if (contato.getEmail() != null && !contato.getEmail().equals(request.getEmail())) {
        verificarEmailExistente(request.getEmail());
      }
    }

    CodigoVerificacao codigoVerificacao = codigoVerificacaoService.save(TipoCodigoVerificacao.CONTATO, usuario);
    usuario.setCodigoVerificacao(codigoVerificacao);

    try {
      if (houveMudanca) {
        CustomRevisionListener.setDadosAntigos(usuario.toStringMapper());
        contato.aplicarMudancaUpdateCodigo(request);
      }

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
    if (usuario.getDataAceiteTermo() == null) {
      throw new NegocioException(UsuarioErrorsMessageConstants.ACEITE_TERMO_NAO_REALIZADO);
    }
  }

  @Transactional
  @Override
  public void enviarCodigoRecuperacaoSenha(Usuario usuario) {
    CodigoVerificacao codigoVerificacao = codigoVerificacaoService.save(TipoCodigoVerificacao.SENHA, usuario);
    usuario.setCodigoVerificacao(codigoVerificacao);
    usuarioRepository.save(usuario);
    enviarCodigo(usuario, codigoVerificacao, usuario.getPessoaPerfil().getContato().getPreferenciaContato());
  }

  @Transactional
  @Override
  public void delete(UUID id) {
    Usuario usuario = usuarioRepository.getByIdComAnexos(id);

    if (usuario == null) {
      throw new RecursoNaoEncontradoException(UsuarioErrorsMessageConstants.USUARIO_NAO_ENCONTRADO);
    }

    try {
      List<PessoaPerfilAnexo> anexosPerfil = usuario.getPessoaPerfil().getPessoaPerfilAnexo();

      if (!anexosPerfil.isEmpty()) {
        List<Anexo> anexos = anexosPerfil.stream().map(PessoaPerfilAnexo::getAnexo).toList();
        bucketService.deletarArquivos(anexos);
        dadosPessoaPerfilTermoRepository.getAnexoRepository().deleteAll(anexos);
      }

      firebaseService.deletarUsuarioFirebase(usuario.getFirebaseUID());
      usuarioRepository.delete(usuario);
      deletarDadosPessoa(usuario);
    } catch (FirebaseAuthException e) {
      throw new NegocioException(FirebaseErrorsMessageConstants.ERRO_DELETAR_USUARIO);
    }
  }

  private void enviarCodigo(Usuario usuario, CodigoVerificacao codigoVerificacao, TipoContato preferenciaContato) {
    if (TipoContato.EMAIL.equals(preferenciaContato)) {
      emailService.enviarEmail(usuario.getPessoaPerfil().getContato().getEmail(), codigoVerificacao.getCodigo());
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
    if (contato.getEmail() != null) {
      verificarEmailExistente(contato.getEmail());
    }

    if (contato.getNumeroWhatsapp() != null) {
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

  private void deletarDadosPessoa(Usuario usuario) {
    PessoaPerfil perfil = usuario.getPessoaPerfil();
    UUID dadosPessoaId = perfil.getDadosPessoaId();

    if (TipoUsuario.PESSOA_FISICA.equals(perfil.getTipoUsuario())) {
      dadosPessoaPerfilTermoRepository.getDadosPessoaFisicaRepository().deleteById(dadosPessoaId);
    } else {
      dadosPessoaPerfilTermoRepository.getDadosPessoaJuridicaRepository().deleteById(dadosPessoaId);

      if (isCooperativaAssociacao(usuario)) {
        dadosPessoaPerfilTermoRepository.getDadosPessoaFisicaRepository().deleteById(perfil.getPresidente().getDadosPessoaId());
      }
    }
  }
}