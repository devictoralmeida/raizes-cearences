package com.devictoralmeida.teste.controllers;

import com.devictoralmeida.teste.dto.request.LoginRequestDto;
import com.devictoralmeida.teste.dto.request.RefreshTokenRequestDto;
import com.devictoralmeida.teste.dto.request.SenhaRequestDto;
import com.devictoralmeida.teste.dto.request.update.SenhaUpdateRequestDto;
import com.devictoralmeida.teste.dto.response.FirebaseLoginResponseDto;
import com.devictoralmeida.teste.dto.response.RefreshTokenResponseDto;
import com.devictoralmeida.teste.dto.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Auth", description = "Operações para autenticação dos usuários")
public interface AuthController {

    @Operation(summary = "Contrato de rota para desativar um usuário", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", ref = "unauthorized"),
            @ApiResponse(responseCode = "404", ref = "notFound"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
    })
    ResponseEntity<ResponseDto<FirebaseLoginResponseDto>> login(LoginRequestDto loginRequestDto);

    @Operation(summary = "Contrato de rota para atualizar o token", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", ref = "unauthorized"),
            @ApiResponse(responseCode = "404", ref = "notFound"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
    })
    ResponseEntity<ResponseDto<RefreshTokenResponseDto>> atualizarToken(RefreshTokenRequestDto request);

    @Operation(summary = "Contrato de rota para o codigo de recuperar a senha", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", ref = "unauthorized"),
            @ApiResponse(responseCode = "404", ref = "notFound"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
    })
    ResponseEntity<?> enviarCodigoRecuperacaoSenha(String login);

    @Operation(summary = "Contrato de rota para recuperar a senha", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", ref = "unauthorized"),
            @ApiResponse(responseCode = "404", ref = "notFound"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
    })
    ResponseEntity<?> redefinirSenha(String login, SenhaRequestDto request);

    @Operation(summary = "Contrato de rota para atualizar a senha do usuario", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", ref = "unauthorized"),
            @ApiResponse(responseCode = "404", ref = "notFound"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
    })
    ResponseEntity<?> alterarSenha(String login, SenhaUpdateRequestDto request);
}
