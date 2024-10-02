package com.devictoralmeida.teste.services;

import com.devictoralmeida.teste.dto.request.UsuarioRequestDto;
import jakarta.validation.Valid;

public interface UsuarioService {
  void save(@Valid UsuarioRequestDto request);
}
