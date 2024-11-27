package com.devictoralmeida.teste.services;

import com.devictoralmeida.teste.dto.request.LoginRequestDto;
import com.devictoralmeida.teste.dto.request.RecuperarSenhaRequestDto;
import com.devictoralmeida.teste.dto.request.RefreshTokenRequestDto;
import com.devictoralmeida.teste.dto.request.SenhaRequestDto;
import com.devictoralmeida.teste.dto.request.update.SenhaUpdateRequestDto;
import com.devictoralmeida.teste.dto.response.FirebaseLoginResponseDto;
import com.devictoralmeida.teste.dto.response.RefreshTokenResponseDto;
import com.devictoralmeida.teste.entities.Usuario;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface AuthService extends UserDetailsService {
  FirebaseLoginResponseDto login(LoginRequestDto request);

  FirebaseToken verificarToken(String idToken);

  RefreshTokenResponseDto atualizarToken(RefreshTokenRequestDto refreshTokenRequestDto);

  void validateSelfOrAdmin(UUID idUsuario);

  void verificarUsuarioValido(Usuario usuario);

  void enviarCodigoRecuperacaoSenha(RecuperarSenhaRequestDto request);

  void recuperarSenha(String login, SenhaRequestDto request);

  void alterarSenha(SenhaUpdateRequestDto request);

  Usuario getUsuarioLogado();
}
