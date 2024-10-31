package com.devictoralmeida.teste.helpers;

import com.devictoralmeida.teste.dto.request.PresidenteRequestDto;
import com.devictoralmeida.teste.shared.constants.SharedConstants;
import com.devictoralmeida.teste.shared.constants.validation.PresidenteValidationMessages;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import com.devictoralmeida.teste.shared.utils.FormatarDadosUtils;

import java.time.LocalDate;

public class PresidenteRequestHelper {
  private PresidenteRequestHelper() {
  }

  public static void validar(PresidenteRequestDto dto) {
    validarDatas(dto);
    DadosPessoaFisicaRequestHelper.validar(dto.getDadosPessoais());
    ContatoRequestHelper.validacoesPresidente(dto.getContato());
    FormatarDadosUtils.aplicarTrim(dto);
  }

  private static void validarDatas(PresidenteRequestDto dto) {
    validarDataAnterior(dto.getDataInicioMandato(), SharedConstants.DATA_MINIMA, PresidenteValidationMessages.DATA_INICIO_MANDATO_PASSADO);
    validarDataAnterior(dto.getDataFinalMandato(), SharedConstants.DATA_MINIMA, PresidenteValidationMessages.DATA_FINAL_MANDATO_PASSADO);
    validarDataAnterior(dto.getDataFinalMandato(), dto.getDataInicioMandato(), PresidenteValidationMessages.DATA_FINAL_MANDATO_ANTERIOR_INICIAL);
    validarDataAnterior(dto.getDataFinalMandato(), dto.getDadosPessoais().getDataNascimento(), PresidenteValidationMessages.DATA_FINAL_MANDATO_ANTERIOR_NASCIMENTO);
    validarDataAnterior(dto.getDataInicioMandato(), dto.getDadosPessoais().getDataNascimento(), PresidenteValidationMessages.DATA_INICIO_MANDATO_ANTERIOR_NASCIMENTO);
  }

  private static void validarDataAnterior(LocalDate data, LocalDate referencia, String mensagemErro) {
    if (data.isBefore(referencia) || data.isEqual(referencia)) {
      throw new NegocioException(mensagemErro);
    }
  }
}
