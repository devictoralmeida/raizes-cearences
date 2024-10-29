package com.devictoralmeida.teste.dto.request;

public class EnderecoRequestDtoTest {
  public static EnderecoRequestDto getEnderecoCompleto() {
    EnderecoRequestDto dto = new EnderecoRequestDto();
    dto.setBairro("Autran Nunes");
    dto.setCep("60527150");
    dto.setComplemento("Casa");
    dto.setLogradouro("Vila Dalmanuta");
    dto.setMunicipio("Fortaleza");
    dto.setUf("CE");
    dto.setNumero("500");
    dto.setPontoReferencia("Proximo ao mercado");
    return dto;
  }
}
