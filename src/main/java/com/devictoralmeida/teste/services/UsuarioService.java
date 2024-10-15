package com.devictoralmeida.teste.services;

import com.devictoralmeida.teste.dto.request.AnexoRequestDto;
import com.devictoralmeida.teste.dto.request.UsuarioRequestDto;
import com.devictoralmeida.teste.entities.Usuario;
import com.google.firebase.auth.FirebaseAuthException;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface UsuarioService {
  void save(@Valid UsuarioRequestDto request) throws FirebaseAuthException;

  void uploadAnexos(String login, List<AnexoRequestDto> anexos);

  Usuario findById(UUID id);

  Usuario findByLogin(String login);

  void validarCodigo(String login, String codigo) throws FirebaseAuthException;

  void enviarCodigo(String login);
}
