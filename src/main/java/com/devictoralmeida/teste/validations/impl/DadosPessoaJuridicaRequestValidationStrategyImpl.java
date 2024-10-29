package com.devictoralmeida.teste.validations.impl;

import com.devictoralmeida.teste.dto.request.DadosPessoaJuridicaRequestDto;
import com.devictoralmeida.teste.shared.constants.SharedConstants;
import com.devictoralmeida.teste.shared.constants.validation.DadosPessoaJuridicaValidationMessages;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import com.devictoralmeida.teste.shared.utils.ValidarDadosUtils;
import com.devictoralmeida.teste.validations.CooperativaAssociacaoValidationStrategy;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class DadosPessoaJuridicaRequestValidationStrategyImpl implements CooperativaAssociacaoValidationStrategy<DadosPessoaJuridicaRequestDto>, Serializable {
  @Serial
  private static final long serialVersionUID = -6453655793972843818L;

  private DadosPessoaJuridicaRequestDto dto;

  @Override
  public void validarCooperativaAssociacao(DadosPessoaJuridicaRequestDto dto) {
    this.dto = dto;
    validarCampoObrigatorio(dto.getNomeFantasia(), DadosPessoaJuridicaValidationMessages.NOME_FANTASIA_OBRIGATORIO);
    validarData(dto.getDataFundacao(), DadosPessoaJuridicaValidationMessages.DATA_FUNDACAO_OBRIGATORIA, DadosPessoaJuridicaValidationMessages.DATA_FUNDACAO_PASSADO);
    validarCampoObrigatorio(dto.getInscricaoJuntaComercial(), DadosPessoaJuridicaValidationMessages.INSCRICAO_JUNTA_COMERCIAL_OBRIGATORIA);
    validarCampoObrigatorio(dto.getInscricaoEstadual(), DadosPessoaJuridicaValidationMessages.INSCRICAO_ESTADUAL_OBRIGATORIA);
    validarData(dto.getDataValidadeCaf(), null, DadosPessoaJuridicaValidationMessages.DATA_VALIDADE_CAF_PASSADO);
  }

  @Override
  public void validar(DadosPessoaJuridicaRequestDto dto) {
    this.dto = dto;
    validarTamanhoCampo(dto.getRazaoSocial(), SharedConstants.TAMANHO_PADRAO_NOME_RAZAO_SOCIAL, DadosPessoaJuridicaValidationMessages.RAZAO_SOCIAL_TAMANHO_PADRAO);
    validarTamanhoCampo(dto.getNomeFantasia(), SharedConstants.TAMANHO_PADRAO_NOME_RAZAO_SOCIAL, DadosPessoaJuridicaValidationMessages.NOME_FANTASIA_TAMANHO_PADRAO);
    resetarCamposNaoPermitidos();
  }

  private void validarTamanhoCampo(String campo, int tamanhoMaximo, String mensagemErro) {
    if (campo != null && campo.length() > tamanhoMaximo) {
      throw new NegocioException(mensagemErro);
    }
  }

  private void resetarCamposNaoPermitidos() {
    dto.setDataValidadeCaf(null);
    dto.setCaf(null);
    dto.setDataFundacao(null);
    dto.setInscricaoEstadual(null);
    dto.setInscricaoJuntaComercial(null);
  }

  private void validarCampoObrigatorio(String campo, String mensagemErro) {
    if (ValidarDadosUtils.isNullOrStringVazia(campo)) {
      throw new NegocioException(mensagemErro);
    }
  }

  private void validarData(LocalDate data, String mensagemErroObrigatorio, String mensagemErroPassado) {
    if (data == null) {
      if (mensagemErroObrigatorio != null) {
        throw new NegocioException(mensagemErroObrigatorio);
      }
    } else if (data.isBefore(SharedConstants.DATA_MINIMA)) {
      throw new NegocioException(mensagemErroPassado);
    }
  }
}
