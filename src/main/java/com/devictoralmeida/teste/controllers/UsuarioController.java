package com.devictoralmeida.teste.controllers;

import com.devictoralmeida.teste.dto.request.CodigoRequestDto;
import com.devictoralmeida.teste.dto.request.SenhaRequestDto;
import com.devictoralmeida.teste.dto.request.UsuarioRequestDto;
import com.devictoralmeida.teste.dto.request.update.ContatoUpdateRequestDto;
import com.devictoralmeida.teste.enums.TipoDocumento;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Tag(name = "Users", description = "Rotas para gerenciar usuários")
public interface UsuarioController {

    @Operation(summary = "Contrato de rota para salvar um usuario", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", ref = "unauthorized"),
            @ApiResponse(responseCode = "404", ref = "notFound"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
    })
    ResponseEntity<?> save(UsuarioRequestDto request);

    @Operation(summary = "Contrato de rota para logar um usuario", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", ref = "unauthorized"),
            @ApiResponse(responseCode = "404", ref = "notFound"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
    })
    ResponseEntity<?> criarSenha(String login, SenhaRequestDto request);

    @Operation(summary = "Contrato de rota para atualizar os documentos de um user no login", responses = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "401", ref = "unauthorized"),
        @ApiResponse(responseCode = "404", ref = "notFound"),
        @ApiResponse(responseCode = "500", ref = "internalServerError")
    })
    ResponseEntity<?> upload(String login, List<TipoDocumento> tipoDocumentos, List<MultipartFile> arquivos);


    @Operation(summary = "Contrato de rota para reenvio de codigo de varificação", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", ref = "unauthorized"),
            @ApiResponse(responseCode = "404", ref = "notFound"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
    })
    ResponseEntity<?> reenvioCodigo(String login);

    @Operation(summary = "Contrato de rota para validar codigo", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", ref = "unauthorized"),
            @ApiResponse(responseCode = "404", ref = "notFound"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
    })
    ResponseEntity<?> validarCodigo(String login, CodigoRequestDto request);

    @Operation(summary = "Contrato de rota para alterar contrato", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", ref = "unauthorized"),
            @ApiResponse(responseCode = "404", ref = "notFound"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
    })
    ResponseEntity<?> alterarContato(String login, ContatoUpdateRequestDto request) throws JsonProcessingException;

    @Operation(summary = "Contrato de rota para desativar um usuário", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", ref = "unauthorized"),
            @ApiResponse(responseCode = "404", ref = "notFound"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
    })
    ResponseEntity<?> delete(UUID id);
}
