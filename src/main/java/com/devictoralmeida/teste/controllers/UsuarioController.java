package com.devictoralmeida.teste.controllers;

import com.devictoralmeida.teste.dto.request.AnexoRequestDto;
import com.devictoralmeida.teste.dto.request.CodigoRequestDto;
import com.devictoralmeida.teste.dto.request.UsuarioRequestDto;
import com.devictoralmeida.teste.dto.response.ResponseDto;
import com.devictoralmeida.teste.enums.TipoContato;
import com.devictoralmeida.teste.enums.TipoDocumento;
import com.devictoralmeida.teste.services.UsuarioService;
import com.devictoralmeida.teste.shared.constants.MessageCommonsConstants;
import com.devictoralmeida.teste.shared.constants.validation.AnexoValidationMessages;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "usuario")
@RequiredArgsConstructor
public class UsuarioController {
  private final UsuarioService service;

  @PostMapping
  public ResponseEntity<?> save(@Valid @RequestBody UsuarioRequestDto request) {
    service.save(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.fromData(null, HttpStatus.CREATED, MessageCommonsConstants.MENSAGEM_SAVE_SUCESSO));
  }

  @PostMapping("/upload/{login}")
  public ResponseEntity<?> upload(@PathVariable(name = "login") String login,
                                  @RequestParam("tipoDocumento") List<TipoDocumento> tipoDocumentos,
                                  @RequestParam("arquivo") List<MultipartFile> arquivos) {
    if (tipoDocumentos.size() != arquivos.size()) {
      throw new NegocioException(AnexoValidationMessages.QUANTIDADE_TIPO_DOCUMENTO_ARQUIVO_DIFERENTE);
    }

    List<AnexoRequestDto> anexos = new ArrayList<>();
    for (int i = 0; i < tipoDocumentos.size(); i++) {
      AnexoRequestDto anexo = new AnexoRequestDto(tipoDocumentos.get(i), arquivos.get(i));
      anexos.add(anexo);
    }

    service.uploadAnexos(login, anexos);
    return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.fromData(null, HttpStatus.CREATED, MessageCommonsConstants.MENSAGEM_UPLOAD_SUCESSO));
  }

  @PostMapping("/reenviar-codigo/{login}")
  public ResponseEntity<?> reenvioCodigo(@PathVariable(name = "login") String login, TipoContato canalEnvio) {
    service.reenviarCodigo(login, canalEnvio);
    return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(null, HttpStatus.OK, MessageCommonsConstants.MENSAGEM_CODIGO_ENVIADO_SUCESSO));
  }

  @PostMapping("/validacao-codigo/{login}")
  public ResponseEntity<?> validarCodigo(@PathVariable(name = "login") String login, @Valid @RequestBody CodigoRequestDto request) {
    service.validarCodigo(login, request.getCodigo());
    return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.fromData(null, HttpStatus.OK, MessageCommonsConstants.MENSAGEM_CADASTRO_CONFIRMADO_SUCESSO));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable(name = "id") UUID id) {
    service.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseDto.fromData(null, HttpStatus.NO_CONTENT, MessageCommonsConstants.MENSAGEM_DELETE_SUCESSO));
  }
}
