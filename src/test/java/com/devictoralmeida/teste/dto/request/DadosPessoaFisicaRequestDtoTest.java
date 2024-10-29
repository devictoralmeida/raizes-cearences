package com.devictoralmeida.teste.dto.request;

import com.devictoralmeida.teste.enums.Sexo;

import java.time.LocalDate;

public class DadosPessoaFisicaRequestDtoTest {
  public static DadosPessoaFisicaRequestDto getDadosPessoaFisica() {
    DadosPessoaFisicaRequestDto dto = new DadosPessoaFisicaRequestDto();
    dto.setNome("Nome");
    dto.setSobrenome("Sobrenome");
    dto.setRg("123456789");
    dto.setOrgaoExpeditor("SSP");
    dto.setDataExpedicao(null);
    dto.setDataNascimento(LocalDate.of(2000, 1, 1));
    dto.setSexo(Sexo.FEMININO);
    dto.setGrauInstrucao(null);
    return dto;
  }
}
