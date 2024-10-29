package com.devictoralmeida.teste.services.impl;

import com.devictoralmeida.teste.dto.request.*;
import com.devictoralmeida.teste.dto.request.update.SenhaUpdateRequestDto;
import com.devictoralmeida.teste.dto.response.FirebaseLoginResponseDto;
import com.devictoralmeida.teste.dto.response.RefreshTokenResponseDto;
import com.devictoralmeida.teste.entities.Usuario;
import com.devictoralmeida.teste.enums.TipoCodigoVerificacao;
import com.devictoralmeida.teste.services.AuthService;
import com.devictoralmeida.teste.services.FirebaseService;
import com.devictoralmeida.teste.services.UsuarioService;
import com.devictoralmeida.teste.shared.constants.errors.AuthErrorsMessageConstants;
import com.devictoralmeida.teste.shared.constants.errors.UsuarioErrorsMessageConstants;
import com.devictoralmeida.teste.shared.exceptions.RecursoNaoEncontradoException;
import com.devictoralmeida.teste.shared.exceptions.SemAutorizacaoException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private static final String API_KEY_PARAM = "key";
  private final UsuarioService usuarioService;
  private final FirebaseService firebaseService;

  @Value("${google.login.url}")
  private String googleLoginUrl;

  @Value("${refresh.token.url}")
  private String refreshTokenUrl;

  @Value("${firebase.api.key}")
  private String apiKey;

  @Transactional(readOnly = true)
  @Override
  public FirebaseLoginResponseDto login(LoginRequestDto request) {
    Usuario usuario = usuarioService.findByLogin(request.getLogin());
    verificarUsuarioValido(usuario);
    UserRecord firebaseUser = firebaseService.findUserByUid(usuario.getFirebaseUID());
    FirebaseLoginRequestDto firebaseLoginRequestDto = new FirebaseLoginRequestDto(firebaseUser.getEmail(), request.getSenha());
    return firebaseLogin(firebaseLoginRequestDto);
  }

  @Override
  public FirebaseToken verificarToken(String idToken) {
    return firebaseService.verificarToken(idToken);
  }

  @Transactional(readOnly = true)
  @Override
  public UserDetails loadUserByUsername(String uid) {
    try {
      return usuarioService.findByFirebaseUID(uid);
    } catch (UsernameNotFoundException e) {
      throw new RecursoNaoEncontradoException(UsuarioErrorsMessageConstants.USUARIO_NAO_ENCONTRADO);
    }
  }

  @Override
  public RefreshTokenResponseDto atualizarToken(RefreshTokenRequestDto refreshTokenRequestDto) {
    return refreshTokenRequest(refreshTokenRequestDto);
  }

  @Override
  public void verificarUsuarioValido(Usuario usuario) {
    if (TipoCodigoVerificacao.CONTATO.equals(usuario.getCodigoVerificacao().getTipoCodigo())) {
      usuarioService.verificarValidacaoCodigo(usuario);
    }

    usuarioService.verificarAceiteTermos(usuario);
  }

  @Override
  public void enviarCodigoRecuperacaoSenha(RecuperarSenhaRequestDto request) {
    Usuario usuario = usuarioService.findByLogin(request.getLogin());
    usuarioService.enviarCodigoRecuperacaoSenha(usuario);
  }

  @Override
  public void recuperarSenha(String login, SenhaRequestDto request) {
    usuarioService.criarSenha(login, request);
  }

  @Transactional
  @Override
  public void alterarSenha(SenhaUpdateRequestDto request) {
    request.validar();
    Usuario usuario = usuarioService.findByLogin(request.getLogin());
    verificarUsuarioValido(usuario);
    UserRecord firebaseUser = firebaseService.findUserByUid(usuario.getFirebaseUID());
    FirebaseLoginRequestDto firebaseLoginRequestDto = new FirebaseLoginRequestDto(firebaseUser.getEmail(), request.getSenhaAtual());
    firebaseLogin(firebaseLoginRequestDto);
    usuarioService.alterarSenha(usuario, request.getNovaSenha());
  }

  private FirebaseLoginResponseDto firebaseLogin(FirebaseLoginRequestDto request) {
    try {
      return RestClient.create(googleLoginUrl)
              .post()
              .uri(uriBuilder -> uriBuilder
                      .queryParam(API_KEY_PARAM, apiKey)
                      .build())
              .body(request)
              .contentType(MediaType.APPLICATION_JSON)
              .retrieve()
              .body(FirebaseLoginResponseDto.class);
    } catch (HttpClientErrorException exception) {
      if (exception.getResponseBodyAsString().contains(AuthErrorsMessageConstants.INVALID_CREDENTIALS_ERROR)) {
        throw new SemAutorizacaoException(AuthErrorsMessageConstants.ERRO_CREDENCIAIS_INVALIDAS);
      }
      throw exception;
    }
  }

  private RefreshTokenResponseDto refreshTokenRequest(RefreshTokenRequestDto refreshTokenRequestDto) {
    try {
      return RestClient.create(refreshTokenUrl)
              .post()
              .uri(uriBuilder -> uriBuilder
                      .queryParam(API_KEY_PARAM, apiKey)
                      .build())
              .body(refreshTokenRequestDto)
              .contentType(MediaType.APPLICATION_JSON)
              .retrieve()
              .body(RefreshTokenResponseDto.class);
    } catch (HttpClientErrorException exception) {
      if (exception.getResponseBodyAsString().contains(AuthErrorsMessageConstants.INVALID_REFRESH_TOKEN_ERROR)) {
        throw new SemAutorizacaoException(AuthErrorsMessageConstants.ERRO_REFRESH_TOKEN_INVALIDO);
      }
      throw exception;
    }
  }
}