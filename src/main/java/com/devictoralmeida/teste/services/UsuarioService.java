package com.devictoralmeida.teste.services;

import com.devictoralmeida.teste.dto.request.AnexoRequestDto;
import com.devictoralmeida.teste.dto.request.UsuarioRequestDto;
import com.devictoralmeida.teste.entities.Usuario;
import com.devictoralmeida.teste.enums.TipoContato;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface UsuarioService {
  void save(@Valid UsuarioRequestDto request);

  void uploadAnexos(String login, List<AnexoRequestDto> anexos);

  Usuario findById(UUID id);

  Usuario findByLogin(String login);

  Usuario findByFirebaseUID(String uid);

  void validarCodigo(String login, String codigo);

  void reenviarCodigo(String login, TipoContato canalEnvio);

  void delete(UUID id);
}
