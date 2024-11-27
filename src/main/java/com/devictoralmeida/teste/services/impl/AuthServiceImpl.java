package com.devictoralmeida.teste.services.impl;

import com.devictoralmeida.teste.dto.request.FirebaseLoginRequestDto;
import com.devictoralmeida.teste.dto.request.LoginRequestDto;
import com.devictoralmeida.teste.dto.request.RefreshTokenRequestDto;
import com.devictoralmeida.teste.dto.request.SenhaRequestDto;
import com.devictoralmeida.teste.dto.request.update.SenhaUpdateRequestDto;
import com.devictoralmeida.teste.dto.response.FirebaseLoginResponseDto;
import com.devictoralmeida.teste.dto.response.RefreshTokenResponseDto;
import com.devictoralmeida.teste.entities.Usuario;
import com.devictoralmeida.teste.services.AuthService;
import com.devictoralmeida.teste.services.FirebaseService;
import com.devictoralmeida.teste.services.UsuarioService;
import com.devictoralmeida.teste.shared.constants.errors.AuthErrorsMessageConstants;
import com.devictoralmeida.teste.shared.constants.errors.UsuarioErrorsMessageConstants;
import com.devictoralmeida.teste.shared.constants.validation.UsuarioValidationMessages;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import com.devictoralmeida.teste.shared.exceptions.RecursoNaoEncontradoException;
import com.devictoralmeida.teste.shared.exceptions.SemAutenticacaoException;
import com.devictoralmeida.teste.shared.exceptions.SemAutorizacaoException;
import com.devictoralmeida.teste.shared.utils.ValidarDadosUtils;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private static final String API_KEY_PARAM = "key";
  private final UsuarioService usuarioService;
  private final FirebaseService firebaseService;

  @Value("${GOOGLE.SENHA.URL}")
  private String checkPasswordUrl;

  @Value("${GOOGLE.LOGIN.URL}")
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
    checkPassword(firebaseLoginRequestDto);
    return firebaseLogin(firebaseLoginRequestDto);
  }


  @Override
  public FirebaseToken verificarToken(String idToken) {
    return firebaseService.verificarToken(idToken);
  }

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
  public void validateSelfOrAdmin(UUID idUsuario) {
    Usuario usuario = getUsuarioLogado();

    if (!usuario.getId().equals(idUsuario) && !usuario.isAdmin()) {
      throw new SemAutorizacaoException(AuthErrorsMessageConstants.ERRO_SEM_PERMISSAO);
    }
  }

  // Esse metodo deve ser protected (nome será autenticado)
  @Override
  public Usuario getUsuarioLogado() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return usuarioService.getUsuarioLogadoByLogin(authentication.getName());
  }

//  public Usuario teste() {
//    Usuario usuario = this.getUsuarioLogado();
//    return new UsuarioResponseDTO(usuario);
//  }

  @Override
  public void verificarUsuarioValido(Usuario usuario) {
    usuarioService.verificarValidacaoCodigo(usuario);
    usuarioService.verificarAceiteTermos(usuario);
  }

  @Override
  public void enviarCodigoRecuperacaoSenha(String login) {
    validarLogin(login);
    Usuario usuario = usuarioService.findByLogin(login);
    usuarioService.enviarCodigoRecuperacaoSenha(usuario);
  }

  @Override
  public void redefinirSenha(String login, SenhaRequestDto request) {
    validarLogin(login);
    usuarioService.criarSenha(login, request);
  }

  @Transactional
  @Override
  public void alterarSenha(String login, SenhaUpdateRequestDto request) {
    validarLogin(login);
    request.validar();
    Usuario usuario = usuarioService.findByLogin(login);
    verificarUsuarioValido(usuario);
    UserRecord firebaseUser = firebaseService.findUserByUid(usuario.getFirebaseUID());
    FirebaseLoginRequestDto firebaseLoginRequestDto = new FirebaseLoginRequestDto(firebaseUser.getEmail(), request.getSenhaAtual());
    checkPassword(firebaseLoginRequestDto);
    firebaseLogin(firebaseLoginRequestDto);
    usuarioService.alterarSenha(usuario, request.getNovaSenha());
  }

  private void validarLogin(String login) {
    if (ValidarDadosUtils.isNullOrStringVazia(login)) {
      throw new NegocioException(UsuarioValidationMessages.LOGIN_OBRIGATORIO);
    }
  }

  private FirebaseLoginResponseDto firebaseLogin(FirebaseLoginRequestDto request) {
    return RestClient.create(googleLoginUrl)
            .post()
            .uri(uriBuilder -> uriBuilder
                    .queryParam(API_KEY_PARAM, apiKey)
                    .build())
            .body(request)
            .contentType(MediaType.APPLICATION_JSON)
            .retrieve()
            .body(FirebaseLoginResponseDto.class);

  }

  private void checkPassword(FirebaseLoginRequestDto request) {
    try {
      RestClient.create(checkPasswordUrl)
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
        throw new SemAutenticacaoException(AuthErrorsMessageConstants.ERRO_CREDENCIAIS_INVALIDAS);
      }
      throw new NegocioException(exception.getResponseBodyAsString());
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
        throw new SemAutenticacaoException(AuthErrorsMessageConstants.ERRO_REFRESH_TOKEN_INVALIDO);
      }
      throw exception;
    }
  }
}