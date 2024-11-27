package com.devictoralmeida.teste.controllers;

import com.devictoralmeida.teste.dto.request.LoginRequestDto;
import com.devictoralmeida.teste.dto.request.RefreshTokenRequestDto;
import com.devictoralmeida.teste.dto.request.SenhaRequestDto;
import com.devictoralmeida.teste.dto.request.update.SenhaUpdateRequestDto;
import com.devictoralmeida.teste.dto.response.FirebaseLoginResponseDto;
import com.devictoralmeida.teste.dto.response.RefreshTokenResponseDto;
import com.devictoralmeida.teste.dto.response.ResponseDto;
import com.devictoralmeida.teste.shared.constants.GlobalExceptionConstants;
import com.devictoralmeida.teste.shared.constants.MessageCommonsConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Auth", description = "Operações para autenticação dos usuários")
public interface AuthController {

    @Operation(summary = "Contrato de rota para desativar um usuário", responses = {
            @ApiResponse(responseCode = "200", ref = MessageCommonsConstants.MENSAGEM_LOGIN_SUCESSO),
            @ApiResponse(responseCode = "401", ref = GlobalExceptionConstants.MENSAGEM_ERRO_AUTENTICACAO),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
    })
    ResponseEntity<ResponseDto<FirebaseLoginResponseDto>> login(LoginRequestDto loginRequestDto);

    @Operation(summary = "Contrato de rota para atualizar o token", responses = {
            @ApiResponse(responseCode = "200", ref = MessageCommonsConstants.MENSAGEM_LOGIN_SUCESSO),
            @ApiResponse(responseCode = "401", ref = GlobalExceptionConstants.MENSAGEM_ERRO_AUTENTICACAO),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
    })
    ResponseEntity<ResponseDto<RefreshTokenResponseDto>> atualizarToken(RefreshTokenRequestDto request);

    @Operation(summary = "Contrato de rota para o codigo de recuperar a senha", responses = {
            @ApiResponse(responseCode = "200", ref = MessageCommonsConstants.MENSAGEM_LOGIN_SUCESSO),
            @ApiResponse(responseCode = "401", ref = GlobalExceptionConstants.MENSAGEM_ERRO_AUTENTICACAO),
    })
    ResponseEntity<?> enviarCodigoRecuperacaoSenha(String login);

    @Operation(summary = "Contrato de rota para recuperar a senha", responses = {
            @ApiResponse(responseCode = "200", ref = MessageCommonsConstants.MENSAGEM_LOGIN_SUCESSO),
            @ApiResponse(responseCode = "401", ref = GlobalExceptionConstants.MENSAGEM_ERRO_AUTENTICACAO),
    })
    ResponseEntity<?> redefinirSenha(String login, SenhaRequestDto request);

    @Operation(summary = "Contrato de rota para atualizar a senha do usuario", responses = {
            @ApiResponse(responseCode = "200", ref = MessageCommonsConstants.MENSAGEM_LOGIN_SUCESSO),
            @ApiResponse(responseCode = "401", ref = GlobalExceptionConstants.MENSAGEM_ERRO_AUTENTICACAO),
            @ApiResponse(responseCode = "404", ref = GlobalExceptionConstants.MENSAGEM_NAO_ENCONTRADO),
    })
    ResponseEntity<?> alterarSenha(String login, SenhaUpdateRequestDto request);
}
