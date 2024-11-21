package com.devictoralmeida.teste.helpers;

import com.devictoralmeida.teste.dto.request.DadosPessoaFisicaRequestDto;
import com.devictoralmeida.teste.shared.constants.SharedConstants;
import com.devictoralmeida.teste.shared.constants.validation.DadosPessoaFisicaValidationMessages;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import com.devictoralmeida.teste.shared.utils.FormatarDadosUtils;

public class DadosPessoaFisicaRequestHelper {
  private DadosPessoaFisicaRequestHelper() {
  }

  public static void validar(DadosPessoaFisicaRequestDto dto) {
    validarDatas(dto);
    FormatarDadosUtils.aplicarTrim(dto);
  }

  private static void validarDatas(DadosPessoaFisicaRequestDto dto) {
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
