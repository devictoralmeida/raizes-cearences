package com.devictoralmeida.teste.controllers;

import com.devictoralmeida.teste.dto.request.CodigoRequestDto;
import com.devictoralmeida.teste.dto.request.SenhaRequestDto;
import com.devictoralmeida.teste.dto.request.UsuarioRequestDto;
import com.devictoralmeida.teste.dto.request.update.ContatoUpdateRequestDto;
import com.devictoralmeida.teste.enums.TipoDocumento;
import com.devictoralmeida.teste.shared.constants.GlobalExceptionConstants;
import com.devictoralmeida.teste.shared.constants.MessageCommonsConstants;
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

  @Operation(summary = "Contrato de rota para salvar um usuário", responses = {
          @ApiResponse(responseCode = "200", ref = MessageCommonsConstants.MENSAGEM_LOGIN_SUCESSO),
          @ApiResponse(responseCode = "401", ref = GlobalExceptionConstants.MENSAGEM_ERRO_AUTENTICACAO),
  })
  ResponseEntity<?> save(UsuarioRequestDto request);

  @Operation(summary = "Contrato de rota para criar a senha do usuário", responses = {
          @ApiResponse(responseCode = "200", ref = MessageCommonsConstants.MENSAGEM_LOGIN_SUCESSO),
          @ApiResponse(responseCode = "401", ref = GlobalExceptionConstants.MENSAGEM_ERRO_AUTENTICACAO),
  })
  ResponseEntity<?> criarSenha(String login, SenhaRequestDto request);

  @Operation(summary = "Contrato de rota para atualizar os documentos de um usuário", responses = {
          @ApiResponse(responseCode = "200", ref = MessageCommonsConstants.MENSAGEM_LOGIN_SUCESSO),
          @ApiResponse(responseCode = "401", ref = GlobalExceptionConstants.MENSAGEM_ERRO_AUTENTICACAO),
  })
  ResponseEntity<?> upload(String login, List<TipoDocumento> tipoDocumentos, List<MultipartFile> arquivos);


  @Operation(summary = "Contrato de rota para reenvio de codigo de verificação", responses = {
          @ApiResponse(responseCode = "200", ref = MessageCommonsConstants.MENSAGEM_LOGIN_SUCESSO),
          @ApiResponse(responseCode = "401", ref = GlobalExceptionConstants.MENSAGEM_ERRO_AUTENTICACAO),
  })
  ResponseEntity<?> reenvioCodigo(String login);

  @Operation(summary = "Contrato de rota para válidar o código do usuário, seja ele de confirmação de cadastro ou de recuperação de senha", responses = {
          @ApiResponse(responseCode = "200", ref = MessageCommonsConstants.MENSAGEM_LOGIN_SUCESSO),
          @ApiResponse(responseCode = "401", ref = GlobalExceptionConstants.MENSAGEM_ERRO_AUTENTICACAO),
  })
  ResponseEntity<?> validarCodigo(String login, CodigoRequestDto request);

  @Operation(summary = "Contrato de rota para alterar o contato principal do usuário", responses = {
          @ApiResponse(responseCode = "200", ref = MessageCommonsConstants.MENSAGEM_LOGIN_SUCESSO),
          @ApiResponse(responseCode = "401", ref = GlobalExceptionConstants.MENSAGEM_ERRO_AUTENTICACAO),
          @ApiResponse(responseCode = "404", ref = GlobalExceptionConstants.MENSAGEM_NAO_ENCONTRADO),
  })
  ResponseEntity<?> alterarContato(String login, ContatoUpdateRequestDto request) throws JsonProcessingException;

  @Operation(summary = "Contrato de rota para desativar um usuário", responses = {
          @ApiResponse(responseCode = "200", ref = MessageCommonsConstants.MENSAGEM_LOGIN_SUCESSO),
          @ApiResponse(responseCode = "401", ref = GlobalExceptionConstants.MENSAGEM_ERRO_AUTENTICACAO),
          @ApiResponse(responseCode = "404", ref = GlobalExceptionConstants.MENSAGEM_NAO_ENCONTRADO),
  })
  ResponseEntity<?> delete(UUID id);
}
