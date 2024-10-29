package com.devictoralmeida.teste.dto.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class VinculoRequestDtoTest {
  public static VinculoRequestDto getVinculo() {
    return new VinculoRequestDto();
  }

  @Test
  void teste_vinculo() {
    VinculoRequestDto dto = VinculoRequestDtoTest.getVinculo();
    assertFalse(dto.isCadastroSecaf());
    assertFalse(dto.isServicosAter());
    assertFalse(dto.isOfertaCeasa());
  }
}
