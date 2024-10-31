package com.devictoralmeida.teste.helpers;

import com.devictoralmeida.teste.dto.request.SenhaRequestDto;
import com.devictoralmeida.teste.shared.constants.validation.UsuarioValidationMessages;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;

public class SenhaRequestHelper {
  private SenhaRequestHelper() {
  }

  public static void validar(SenhaRequestDto dto) {
    if (!dto.getSenha().equals(dto.getConfirmacaoSenha())) {
      throw new NegocioException(UsuarioValidationMessages.SENHA_DIFERENTE);
    }
  }
}
