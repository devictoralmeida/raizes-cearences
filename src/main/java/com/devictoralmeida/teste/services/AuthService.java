package com.devictoralmeida.teste.services;

import com.devictoralmeida.teste.dto.request.LoginRequestDto;
import com.devictoralmeida.teste.dto.request.RefreshTokenRequestDto;
import com.devictoralmeida.teste.dto.response.FirebaseLoginResponseDto;
import com.devictoralmeida.teste.dto.response.RefreshTokenResponseDto;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {
  FirebaseLoginResponseDto login(LoginRequestDto request);

  FirebaseToken verificarToken(String idToken);

  RefreshTokenResponseDto atualizarToken(RefreshTokenRequestDto refreshTokenRequestDto);
}
