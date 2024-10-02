package com.devictoralmeida.teste.controllers;

import com.devictoralmeida.teste.dto.request.UsuarioRequestDto;
import com.devictoralmeida.teste.dto.response.ResponseDto;
import com.devictoralmeida.teste.services.UsuarioService;
import com.devictoralmeida.teste.shared.constants.MessageCommonsConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
