package com.devictoralmeida.teste.services.impl;

import com.devictoralmeida.teste.dto.request.AnexoRequestDto;
import com.devictoralmeida.teste.dto.request.ContatoRequestDto;
import com.devictoralmeida.teste.dto.request.UsuarioRequestDto;
import com.devictoralmeida.teste.entities.*;
import com.devictoralmeida.teste.enums.TipoContato;
import com.devictoralmeida.teste.enums.TipoPerfil;
import com.devictoralmeida.teste.factories.DadosPessoaPerfilRepository;
import com.devictoralmeida.teste.repositories.UsuarioRepository;
import com.devictoralmeida.teste.services.*;
import com.devictoralmeida.teste.services.rules.RegraPessoaPerfilAnexo;
import com.devictoralmeida.teste.shared.constants.validation.ContatoValidationMessages;
import com.devictoralmeida.teste.shared.constants.validation.UsuarioValidationMessages;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
  private final DadosPessoaPerfilRepository dadosPessoaPerfilRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  @Override
  public void save(UsuarioRequestDto request) {
    request.validar();
    validarDocumentoExistente(request);
    validarContatoExistente(request.getPessoaPerfil().getContato());

    UserRecord usuarioFirebase = firebaseService.criarUsuarioFirebase(request);
    Usuario usuario = new Usuario(request, passwordEncoder.encode(request.getSenha()));
    PessoaPerfil pessoaPerfil = createPessoaPerfil(request, usuario);
    usuario.setPessoaPerfil(pessoaPerfil);
    usuario.setFirebaseUID(usuarioFirebase.getUid());
    CodigoVerificacao codigoVerificacao = codigoVerificacaoService.save(codigoVerificacaoService.generateVerificationCode());
    usuario.setCodigoVerificacao(codigoVerificacao);
    usuarioRepository.save(usuario);

//    TipoContato preferenciaContato = usuario.getPessoaPerfil().getContato().getPreferenciaContato();
//    enviarCodigo(usuario, codigoVerificacao, preferenciaContato);
  }

  @Transactional(readOnly = true)
  @Override
  public Usuario findById(UUID id) {
    return usuarioRepository.findById(id).orElseThrow(() -> new NegocioException(UsuarioValidationMessages.USUARIO_NAO_ENCONTRADO));
  }

  @Transactional(readOnly = true)
  @Override
  public Usuario findByLogin(String login) {
    return usuarioRepository.findByLogin(login).orElseThrow(() -> new NegocioException(UsuarioValidationMessages.USUARIO_NAO_ENCONTRADO));
  }

  @Transactional(readOnly = true)
  @Override
  public Usuario findByFirebaseUID(String uid) {
    return usuarioRepository.findByFirebaseUID(uid).orElseThrow(() -> new NegocioException(UsuarioValidationMessages.USUARIO_NAO_ENCONTRADO + " no Firebase"));
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
  public void reenviarCodigo(String login, TipoContato canalEnvio) {
    Usuario usuario = findByLogin(login);
    Contato contato = usuario.getPessoaPerfil().getContato();

    if (TipoContato.EMAIL.equals(canalEnvio) && contato.getEmail() == null) {
      throw new NegocioException(UsuarioValidationMessages.EMAIL_NAO_CADASTRADO);
    }

    if (TipoContato.WHATSAPP.equals(canalEnvio) && contato.getNumeroWhatsapp() == null) {
      throw new NegocioException(UsuarioValidationMessages.WHATSAPP_NAO_CADASTRADO);
    }

    CodigoVerificacao codigoVerificacao = codigoVerificacaoService.save(codigoVerificacaoService.generateVerificationCode());
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
      anexo.setNome(anexo.getArquivo().getOriginalFilename());
      PessoaPerfilAnexo pessoaPerfilAnexo = new PessoaPerfilAnexo(anexo, usuario);
      usuario.getPessoaPerfil().getAnexos().add(pessoaPerfilAnexo);
      dadosPessoaPerfilRepository.getPessoaPerfilAnexoRepository().save(pessoaPerfilAnexo);
    });
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  @Override
  public void delete(UUID id) {
    Usuario usuario = findById(id);
    firebaseService.deletarUsuarioFirebase(usuario.getFirebaseUID());
    usuarioRepository.delete(usuario);
  }

  @Override
  public FirebaseToken verificarToken(String idToken) throws FirebaseAuthException {
    return firebaseService.verificarToken(idToken);
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
      throw new NegocioException(UsuarioValidationMessages.DOCUMENTO_EXISTENTE);
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
      throw new NegocioException(ContatoValidationMessages.WHATSAPP_JA_CADASTRADO);
    }
  }

  private void verificarEmailExistente(String email) {
    if (usuarioRepository.findByEmail(email).isPresent()) {
      throw new NegocioException(ContatoValidationMessages.EMAIL_JA_CADASTRADO);
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
    dadosPessoaPerfilRepository.getDadosPessoaFisicaRepository().save(dadosPessoaFisica);
    return new PessoaPerfil(request.getPessoaPerfil(), usuario, dadosPessoaFisica.getId());
  }

  private PessoaPerfil createPessoaPerfilJuridica(UsuarioRequestDto request, Usuario usuario) {
    DadosPessoaJuridica dadosPessoaJuridica = new DadosPessoaJuridica(request.getPessoaPerfil().getDadosPessoaJuridica());
    dadosPessoaPerfilRepository.getDadosPessoaJuridicaRepository().save(dadosPessoaJuridica);

    if (isCooperativaAssociacao(usuario)) {
      return createPessoaPerfilComPresidente(request, usuario, dadosPessoaJuridica);
    } else {
      return new PessoaPerfil(request.getPessoaPerfil(), usuario, dadosPessoaJuridica.getId());
    }
  }

  private PessoaPerfil createPessoaPerfilComPresidente(UsuarioRequestDto request, Usuario usuario, DadosPessoaJuridica dadosPessoaJuridica) {
    DadosPessoaFisica dadosPresidente = new DadosPessoaFisica(request.getPessoaPerfil().getPresidente().getDadosPessoais());
    dadosPessoaPerfilRepository.getDadosPessoaFisicaRepository().save(dadosPresidente);
    return new PessoaPerfil(request.getPessoaPerfil(), usuario, dadosPessoaJuridica.getId(), dadosPresidente.getId());
  }

  private boolean isCooperativaAssociacao(Usuario usuario) {
    return TipoPerfil.COOPERATIVA.equals(usuario.getTipoPerfil()) || TipoPerfil.ASSOCIACAO.equals(usuario.getTipoPerfil());
  }

  @Override
  public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
    return findByFirebaseUID(uid);
  }
}