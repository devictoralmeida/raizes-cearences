package com.devictoralmeida.teste.entities;

import com.devictoralmeida.teste.dto.request.EnderecoRequestDto;

import java.util.UUID;

public class EnderecoTest {
  public static Endereco getEndereco(EnderecoRequestDto request) {
    Endereco endereco = new Endereco(request);
    endereco.setId(UUID.fromString("8c7501b1-b2f5-4b30-a3b0-b8f92feb0a63"));
    return endereco;
  }
}
