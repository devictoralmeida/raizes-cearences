package com.devictoralmeida.teste.dto.request;

import com.devictoralmeida.teste.shared.constants.validation.UsuarioValidationMessages;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SenhaRequestDtoTest {
  public static SenhaRequestDto getSenhaValida() {
    SenhaRequestDto dto = new SenhaRequestDto();
    dto.setSenha("Password123!");
    dto.setConfirmacaoSenha("Password123!");
    return dto;
  }

  @Test
  void validar_senhas_iguais() {
    SenhaRequestDto dto = getSenhaValida();
    assertDoesNotThrow(dto::validar);
  }

  @Test
  void validar_senhasDiferentes() {
    SenhaRequestDto dto = getSenhaValida();
    dto.setConfirmacaoSenha("Password1234!");
    NegocioException exception = assertThrows(NegocioException.class, dto::validar);
    assertEquals(UsuarioValidationMessages.SENHA_DIFERENTE, exception.getMessage());
  }

  @Test
  void validar_senhaNula() {
    SenhaRequestDto dto = new SenhaRequestDto();
    dto.setSenha(null);
    dto.setConfirmacaoSenha("Password123!");
    NegocioException exception = assertThrows(NegocioException.class, dto::validar);
    assertEquals(UsuarioValidationMessages.SENHA_OBRIGATORIA, exception.getMessage());
  }

  @Test
  void validar_confirmacaoSenhaNula() {
    SenhaRequestDto dto = new SenhaRequestDto();
    dto.setSenha("Password123!");
    dto.setConfirmacaoSenha(null);
    NegocioException exception = assertThrows(NegocioException.class, dto::validar);
    assertEquals(UsuarioValidationMessages.CONFIRMACAO_SENHA_OBRIGATORIA, exception.getMessage());
  }

  @Test
  void validar_senhaInvalida() {
    SenhaRequestDto dto = new SenhaRequestDto();
    dto.setSenha("pass");
    dto.setConfirmacaoSenha("pass");
    NegocioException exception = assertThrows(NegocioException.class, dto::validar);
    assertEquals(UsuarioValidationMessages.SENHA_TAMANHO, exception.getMessage());
  }
}
