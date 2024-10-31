package com.devictoralmeida.teste.helpers;

import com.devictoralmeida.teste.dto.request.DadosPessoaJuridicaRequestDto;
import com.devictoralmeida.teste.shared.constants.SharedConstants;
import com.devictoralmeida.teste.shared.constants.validation.DadosPessoaJuridicaValidationMessages;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import com.devictoralmeida.teste.shared.utils.FormatarDadosUtils;
import com.devictoralmeida.teste.shared.utils.ValidarDadosUtils;

import java.time.LocalDate;

public class DadosPessoaJuridicaRequestHelper {
  private DadosPessoaJuridicaRequestHelper() {
  }

  public static void validar(DadosPessoaJuridicaRequestDto dto) {
    validarTamanhoCampo(dto.getRazaoSocial(), SharedConstants.TAMANHO_PADRAO_NOME_RAZAO_SOCIAL, DadosPessoaJuridicaValidationMessages.RAZAO_SOCIAL_TAMANHO_PADRAO);
    validarTamanhoCampo(dto.getNomeFantasia(), SharedConstants.TAMANHO_PADRAO_NOME_RAZAO_SOCIAL, DadosPessoaJuridicaValidationMessages.NOME_FANTASIA_TAMANHO_PADRAO);
    resetarCamposNaoPermitidos(dto);
    FormatarDadosUtils.aplicarTrim(dto);
  }

  public static void validarCooperativaAssociacao(DadosPessoaJuridicaRequestDto dto) {
    validarCampoObrigatorio(dto.getNomeFantasia(), DadosPessoaJuridicaValidationMessages.NOME_FANTASIA_OBRIGATORIO);
    validarData(dto.getDataFundacao(), DadosPessoaJuridicaValidationMessages.DATA_FUNDACAO_OBRIGATORIA, DadosPessoaJuridicaValidationMessages.DATA_FUNDACAO_PASSADO);
    validarCampoObrigatorio(dto.getInscricaoJuntaComercial(), DadosPessoaJuridicaValidationMessages.INSCRICAO_JUNTA_COMERCIAL_OBRIGATORIA);
    validarCampoObrigatorio(dto.getInscricaoEstadual(), DadosPessoaJuridicaValidationMessages.INSCRICAO_ESTADUAL_OBRIGATORIA);
    validarData(dto.getDataValidadeCaf(), null, DadosPessoaJuridicaValidationMessages.DATA_VALIDADE_CAF_PASSADO);
    FormatarDadosUtils.aplicarTrim(dto);
  }

  private static void validarTamanhoCampo(String campo, int tamanhoMaximo, String mensagemErro) {
    if (campo != null && campo.length() > tamanhoMaximo) {
      throw new NegocioException(mensagemErro);
    }
  }

  private static void resetarCamposNaoPermitidos(DadosPessoaJuridicaRequestDto dto) {
    dto.setDataValidadeCaf(null);
    dto.setCaf(null);
    dto.setDataFundacao(null);
    dto.setInscricaoEstadual(null);
    dto.setInscricaoJuntaComercial(null);
  }

  private static void validarCampoObrigatorio(String campo, String mensagemErro) {
    if (ValidarDadosUtils.isNullOrStringVazia(campo)) {
      throw new NegocioException(mensagemErro);
    }
  }

  private static void validarData(LocalDate data, String mensagemErroObrigatorio, String mensagemErroPassado) {
    if (data == null) {
      if (mensagemErroObrigatorio != null) {
        throw new NegocioException(mensagemErroObrigatorio);
      }
    } else if (data.isBefore(SharedConstants.DATA_MINIMA)) {
      throw new NegocioException(mensagemErroPassado);
    }
  }
}
