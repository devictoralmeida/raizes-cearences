package com.devictoralmeida.teste.dto.request;

import com.devictoralmeida.teste.shared.constants.SharedConstants;
import com.devictoralmeida.teste.shared.constants.validation.DadosPessoaJuridicaValidationMessages;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import com.devictoralmeida.teste.shared.utils.ValidateDadosUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class DadosPessoaJuridicaRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 9132679813191593067L;

  private UUID id;

  @NotBlank(message = DadosPessoaJuridicaValidationMessages.RAZAO_SOCIAL_OBRIGATORIA)
  @Pattern(regexp = SharedConstants.REGEX_ALFANUMERICO, message = DadosPessoaJuridicaValidationMessages.RAZAO_SOCIAL_ALFANUMERICO)
  @Size(max = 150, message = DadosPessoaJuridicaValidationMessages.RAZAO_SOCIAL_TAMANHO_COOPERATIVA_ASSOCIACAO)
  private String razaoSocial;

  @Pattern(regexp = SharedConstants.REGEX_ALFANUMERICO, message = DadosPessoaJuridicaValidationMessages.NOME_FANTASIA_ALFANUMERICO)
  @Size(max = 150, message = DadosPessoaJuridicaValidationMessages.NOME_FANTASIA_TAMANHO_COOPERATIVA_ASSOCIACAO)
  private String nomeFantasia;

  @Size(max = 50, message = DadosPessoaJuridicaValidationMessages.INSCRICAO_JUNTA_COMERCIAL_TAMANHO)
  private String inscricaoJuntaComercial;

  @Pattern(regexp = SharedConstants.REGEX_APENAS_NUMEROS, message = DadosPessoaJuridicaValidationMessages.INSCRICAO_ESTADUAL_APENAS_NUMEROS)
  @Size(max = 12, message = DadosPessoaJuridicaValidationMessages.INSCRICAO_ESTADUAL_TAMANHO)
  private String inscricaoEstadual;

  private LocalDate dataFundacao;

  @Size(max = 11, message = DadosPessoaJuridicaValidationMessages.CAF_TAMANHO)
  private String caf;

  private LocalDate dataValidadeCaf;

  public void validar() {
    validarTamanhoCampo(razaoSocial, 100, DadosPessoaJuridicaValidationMessages.RAZAO_SOCIAL_TAMANHO_PADRAO);
    validarTamanhoCampo(nomeFantasia, 100, DadosPessoaJuridicaValidationMessages.NOME_FANTASIA_TAMANHO_PADRAO);
    resetarCamposNaoPermitidos();
  }

  private void validarTamanhoCampo(String campo, int tamanhoMaximo, String mensagemErro) {
    if (campo != null && campo.length() > tamanhoMaximo) {
      throw new NegocioException(mensagemErro);
    }
  }

  private void resetarCamposNaoPermitidos() {
    dataValidadeCaf = null;
    caf = null;
    dataFundacao = null;
    inscricaoEstadual = null;
    inscricaoJuntaComercial = null;
  }

  public void validarCooperativaAssociacao() {
    validarCampoObrigatorio(nomeFantasia, DadosPessoaJuridicaValidationMessages.NOME_FANTASIA_OBRIGATORIO);
    validarData(dataFundacao, DadosPessoaJuridicaValidationMessages.DATA_FUNDACAO_OBRIGATORIA, DadosPessoaJuridicaValidationMessages.DATA_FUNDACAO_PASSADO);
    validarCampoObrigatorio(inscricaoJuntaComercial, DadosPessoaJuridicaValidationMessages.INSCRICAO_JUNTA_COMERCIAL_OBRIGATORIA);
    validarCampoObrigatorio(inscricaoEstadual, DadosPessoaJuridicaValidationMessages.INSCRICAO_ESTADUAL_OBRIGATORIA);
    validarData(dataValidadeCaf, null, DadosPessoaJuridicaValidationMessages.DATA_VALIDADE_CAF_PASSADO);
  }

  private void validarCampoObrigatorio(String campo, String mensagemErro) {
    if (ValidateDadosUtils.isNullOrStringVazia(campo)) {
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