package com.devictoralmeida.teste.validations.impl;

import com.devictoralmeida.teste.dto.request.DadosPessoaFisicaRequestDto;
import com.devictoralmeida.teste.shared.constants.SharedConstants;
import com.devictoralmeida.teste.shared.constants.validation.DadosPessoaFisicaValidationMessages;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import com.devictoralmeida.teste.validations.ValidationStrategy;

import java.io.Serial;
import java.io.Serializable;

public class DadosPessoaFisicaRequestValidationStrategyImpl implements ValidationStrategy<DadosPessoaFisicaRequestDto>, Serializable {
  @Serial
  private static final long serialVersionUID = 4622310390061080181L;

  private DadosPessoaFisicaRequestDto dto;

  @Override
  public void validar(DadosPessoaFisicaRequestDto dto) {
    this.dto = dto;
    validarDatas();
  }

  private void validarDatas() {
    if (dto.getDataNascimento().isBefore(SharedConstants.DATA_MINIMA)) {
      throw new NegocioException(DadosPessoaFisicaValidationMessages.DATA_NASCIMENTO_PASSADO);
    }

    if (dto.getDataExpedicao() != null) {
      if (dto.getDataExpedicao().isBefore(SharedConstants.DATA_MINIMA)) {
        throw new NegocioException(DadosPessoaFisicaValidationMessages.DATA_EXPEDICAO_PASSADO);
      }

      if (dto.getDataExpedicao().isBefore(dto.getDataNascimento())) {
        throw new NegocioException(DadosPessoaFisicaValidationMessages.DATA_EXPEDICAO_ANTERIOR_NASCIMENTO);
      }
    }
  }
}
