package com.devictoralmeida.teste.controllers.impl;

import com.devictoralmeida.teste.controllers.AuthController;
import com.devictoralmeida.teste.dto.request.LoginRequestDto;
import com.devictoralmeida.teste.dto.request.RefreshTokenRequestDto;
import com.devictoralmeida.teste.dto.request.SenhaRequestDto;
import com.devictoralmeida.teste.dto.request.update.SenhaUpdateRequestDto;
import com.devictoralmeida.teste.dto.response.FirebaseLoginResponseDto;
import com.devictoralmeida.teste.dto.response.RefreshTokenResponseDto;
import com.devictoralmeida.teste.dto.response.ResponseDto;
import com.devictoralmeida.teste.entities.Usuario;
import com.devictoralmeida.teste.services.AuthService;
import com.devictoralmeida.teste.shared.constants.MessageCommonsConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "auth")
@RequiredArgsConstructor
public class AuthControllerImpl  implements AuthController {
  private final AuthService authService;

  // Metodo de teste para verificar se o usuário está logado
  @GetMapping(value = "/teste")
  public ResponseEntity<ResponseDto<Usuario>> getUsuario() {
    return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(authService.getUsuarioLogado(), HttpStatus.OK, null));
  }

  @Override
  @PostMapping(value = "/login")
  public ResponseEntity<ResponseDto<FirebaseLoginResponseDto>> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.fromData(authService.login(loginRequestDto), HttpStatus.CREATED, MessageCommonsConstants.MENSAGEM_LOGIN_SUCESSO));
  }

  @Override
  @PostMapping(value = "/atualizar-token")
  public ResponseEntity<ResponseDto<RefreshTokenResponseDto>> atualizarToken(@Valid @RequestBody RefreshTokenRequestDto request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.fromData(authService.atualizarToken(request), HttpStatus.CREATED, MessageCommonsConstants.MENSAGEM_TOKEN_ATUALIZADO_SUCESSO));
  }

  @Override
  @PostMapping(value = "/recuperar-senha/{login}")
  public ResponseEntity<?> enviarCodigoRecuperacaoSenha(@PathVariable(name = "login") String login) {
    authService.enviarCodigoRecuperacaoSenha(login);
    return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.fromData(null, HttpStatus.CREATED, MessageCommonsConstants.MENSAGEM_CODIGO_ENVIADO_SUCESSO));
  }

  @Override
  @PatchMapping("/redefinir-senha/{login}")
  public ResponseEntity<?> redefinirSenha(@PathVariable(name = "login") String login, @Valid @RequestBody SenhaRequestDto request) {
    authService.redefinirSenha(login, request);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(null, HttpStatus.OK, MessageCommonsConstants.MENSAGEM_SENHA_REDEFINIDA_SUCESSO));
  }

  @Override
  @PatchMapping("/alterar-senha/{login}")
  public ResponseEntity<?> alterarSenha(@PathVariable(name = "login") String login, @Valid @RequestBody SenhaUpdateRequestDto request) {
    authService.alterarSenha(login, request);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(null, HttpStatus.OK, MessageCommonsConstants.MENSAGEM_SENHA_ALTERADA_SUCESSO));
  }
}
