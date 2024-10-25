package com.devictoralmeida.teste.controllers;

import com.devictoralmeida.teste.dto.request.LoginRequestDto;
import com.devictoralmeida.teste.dto.request.RefreshTokenRequestDto;
import com.devictoralmeida.teste.dto.response.FirebaseLoginResponseDto;
import com.devictoralmeida.teste.dto.response.RefreshTokenResponseDto;
import com.devictoralmeida.teste.dto.response.ResponseDto;
import com.devictoralmeida.teste.services.AuthService;
import com.devictoralmeida.teste.shared.constants.MessageCommonsConstants;
import com.google.firebase.auth.FirebaseAuthException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping(value = "/login")
  public ResponseEntity<ResponseDto<FirebaseLoginResponseDto>> login(@Valid @RequestBody LoginRequestDto loginRequestDto) throws FirebaseAuthException {
    return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(authService.login(loginRequestDto), HttpStatus.OK, MessageCommonsConstants.MENSAGEM_LOGIN_SUCESSO));
  }

  @PostMapping(value = "/atualizar-token")
  public ResponseEntity<ResponseDto<RefreshTokenResponseDto>> atualizarToken(@Valid @RequestBody RefreshTokenRequestDto request) {
    return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(authService.atualizarToken(request), HttpStatus.OK, MessageCommonsConstants.MENSAGEM_TOKEN_ATUALIZADO_SUCESSO));
  }
}
