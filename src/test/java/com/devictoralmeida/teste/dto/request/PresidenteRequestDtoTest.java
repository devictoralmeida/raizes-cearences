package com.devictoralmeida.teste.dto.request;

import java.time.LocalDate;

public class PresidenteRequestDtoTest {
  public static PresidenteRequestDto getPresidente() {
    PresidenteRequestDto dto = new PresidenteRequestDto();
    dto.setDocumento("00321607040");
    dto.setDadosPessoais(DadosPessoaFisicaRequestDtoTest.getDadosPessoaFisica());
    dto.setContato(ContatoRequestDtoTest.getContatoPresidente());
    dto.setDataInicioMandato(LocalDate.of(2021, 6, 1));
    dto.setDataFinalMandato(LocalDate.of(2025, 6, 1));
    return dto;
  }
}
