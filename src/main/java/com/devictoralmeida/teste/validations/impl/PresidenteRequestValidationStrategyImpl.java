package com.devictoralmeida.teste.validations.impl;

import com.devictoralmeida.teste.dto.request.PresidenteRequestDto;
import com.devictoralmeida.teste.shared.constants.SharedConstants;
import com.devictoralmeida.teste.shared.constants.validation.PresidenteValidationMessages;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import com.devictoralmeida.teste.validations.ValidationStrategy;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class PresidenteRequestValidationStrategyImpl implements ValidationStrategy<PresidenteRequestDto>, Serializable {
  @Serial
  private static final long serialVersionUID = -7588359618782441042L;

  private PresidenteRequestDto dto;

  @Override
  public void validar(PresidenteRequestDto dto) {
    this.dto = dto;
    validarDatas();
    dto.getDadosPessoais().validar();
    dto.getContato().validacoesPresidente();
  }

  private void validarDatas() {
    validarDataAnterior(dto.getDataInicioMandato(), SharedConstants.DATA_MINIMA, PresidenteValidationMessages.DATA_INICIO_MANDATO_PASSADO);
    validarDataAnterior(dto.getDataFinalMandato(), SharedConstants.DATA_MINIMA, PresidenteValidationMessages.DATA_FINAL_MANDATO_PASSADO);
    validarDataAnterior(dto.getDataFinalMandato(), dto.getDataInicioMandato(), PresidenteValidationMessages.DATA_FINAL_MANDATO_ANTERIOR_INICIAL);
    validarDataAnterior(dto.getDataFinalMandato(), dto.getDadosPessoais().getDataNascimento(), PresidenteValidationMessages.DATA_FINAL_MANDATO_ANTERIOR_NASCIMENTO);
    validarDataAnterior(dto.getDataInicioMandato(), dto.getDadosPessoais().getDataNascimento(), PresidenteValidationMessages.DATA_INICIO_MANDATO_ANTERIOR_NASCIMENTO);
  }

  private void validarDataAnterior(LocalDate data, LocalDate referencia, String mensagemErro) {
    if (data.isBefore(referencia) || data.isEqual(referencia)) {
      throw new NegocioException(mensagemErro);
    }
  }
}
