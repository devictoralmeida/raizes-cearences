package com.devictoralmeida.teste.dto.request;

import com.devictoralmeida.teste.shared.constants.validation.DadosPessoaJuridicaValidationMessages;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class DadosPessoaJuridicaRequestDtoTest {
  public static DadosPessoaJuridicaRequestDto getDadosPessoaJuridicaCompleto() {
    DadosPessoaJuridicaRequestDto dto = new DadosPessoaJuridicaRequestDto();
    dto.setRazaoSocial("Razao Social");
    dto.setNomeFantasia("Nome Fantasia");
    dto.setInscricaoJuntaComercial("12345");
    dto.setInscricaoEstadual("12345");
    dto.setDataFundacao(LocalDate.of(1990, 6, 1));
    dto.setCaf("12345678901");
    dto.setDataValidadeCaf(LocalDate.of(2026, 10, 25));
    return dto;
  }

  public static DadosPessoaJuridicaRequestDto getDadosPessoaJuridicaParcial() {
    DadosPessoaJuridicaRequestDto dto = new DadosPessoaJuridicaRequestDto();
    dto.setRazaoSocial("Razao Social");
    return dto;
  }

  @Test
  void test_validacao() {
    DadosPessoaJuridicaRequestDto dto = DadosPessoaJuridicaRequestDtoTest.getDadosPessoaJuridicaCompleto();
    dto.validar();
    assertNull(dto.getDataValidadeCaf());
    assertNull(dto.getCaf());
    assertNull(dto.getDataFundacao());
    assertNull(dto.getInscricaoEstadual());
    assertNull(dto.getInscricaoJuntaComercial());
  }

  @Test
  void test_validacao_cooperativa_associacao() {
    DadosPessoaJuridicaRequestDto dto = DadosPessoaJuridicaRequestDtoTest.getDadosPessoaJuridicaCompleto();
    dto.setNomeFantasia(null);
    NegocioException exception = assertThrows(NegocioException.class, dto::validarCooperativaAssociacao);
    assertEquals(DadosPessoaJuridicaValidationMessages.NOME_FANTASIA_OBRIGATORIO, exception.getMessage());

    dto.setNomeFantasia("Nome Fantasia");
    dto.setInscricaoJuntaComercial(null);
    exception = assertThrows(NegocioException.class, dto::validarCooperativaAssociacao);
    assertEquals(DadosPessoaJuridicaValidationMessages.INSCRICAO_JUNTA_COMERCIAL_OBRIGATORIA, exception.getMessage());

    dto.setInscricaoJuntaComercial("12345");
    dto.setInscricaoEstadual(null);
    exception = assertThrows(NegocioException.class, dto::validarCooperativaAssociacao);
    assertEquals(DadosPessoaJuridicaValidationMessages.INSCRICAO_ESTADUAL_OBRIGATORIA, exception.getMessage());

    dto.setInscricaoEstadual("12345");
    dto.setDataFundacao(null);
    exception = assertThrows(NegocioException.class, dto::validarCooperativaAssociacao);
    assertEquals(DadosPessoaJuridicaValidationMessages.DATA_FUNDACAO_OBRIGATORIA, exception.getMessage());
  }
}
