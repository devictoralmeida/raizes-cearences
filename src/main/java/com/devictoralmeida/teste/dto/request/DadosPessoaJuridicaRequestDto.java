package com.devictoralmeida.teste.dto.request;

import com.devictoralmeida.teste.shared.constants.SharedConstants;
import com.devictoralmeida.teste.shared.constants.validation.DadosPessoaJuridicaValidationMessages;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import com.devictoralmeida.teste.shared.utils.ValidateDadosUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
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

  @Size(max = 150, message = DadosPessoaJuridicaValidationMessages.SIGLA_TAMANHO)
  private String sigla;

  @NotBlank(message = DadosPessoaJuridicaValidationMessages.RAZAO_SOCIAL_OBRIGATORIA)
  @Size(max = 100, message = DadosPessoaJuridicaValidationMessages.RAZAO_SOCIAL_TAMANHO)
  private String razaoSocial;

  @Size(max = 100, message = DadosPessoaJuridicaValidationMessages.NOME_FANTASIA_TAMANHO)
  private String nomeFantasia;

  @Pattern(regexp = SharedConstants.REGEX_APENAS_NUMEROS, message = DadosPessoaJuridicaValidationMessages.INSCRICAO_JUNTA_COMERCIAL_APENAS_NUMEROS)
  @Size(max = 50, message = DadosPessoaJuridicaValidationMessages.INSCRICAO_JUNTA_COMERCIAL_TAMANHO)
  private String inscricaoJuntaComercial;

  @Size(max = 12, message = DadosPessoaJuridicaValidationMessages.INSCRICAO_ESTADUAL_TAMANHO)
  private String inscricaoEstadual;

  @Past(message = DadosPessoaJuridicaValidationMessages.DATA_FUNDACAO_PASSADO_PRESENTE)
  private LocalDate dataFundacao;

  public void validarCooperativaAssociacao() {
    validarSigla();
    validarNomeFantasia();
    validarDataFundacao();
    validarInscricaoJuntaComercial();
    validarInscricaoEstadual();
  }

  private void validarSigla() {
    if (ValidateDadosUtils.isNullOrStringVazia(sigla)) {
      throw new NegocioException(DadosPessoaJuridicaValidationMessages.SIGLA_OBRIGATORIA);
    }
  }

  private void validarNomeFantasia() {
    if (ValidateDadosUtils.isNullOrStringVazia(nomeFantasia)) {
      throw new NegocioException(DadosPessoaJuridicaValidationMessages.NOME_FANTASIA_OBRIGATORIO);
    }
  }

  private void validarDataFundacao() {
    if (dataFundacao == null) {
      throw new NegocioException(DadosPessoaJuridicaValidationMessages.DATA_FUNDACAO_OBRIGATORIA);
    }
  }

  private void validarInscricaoJuntaComercial() {
    if (ValidateDadosUtils.isNullOrStringVazia(inscricaoJuntaComercial)) {
      throw new NegocioException(DadosPessoaJuridicaValidationMessages.INSCRICAO_JUNTA_COMERCIAL_OBRIGATORIA);
    }
  }

  private void validarInscricaoEstadual() {
    if (ValidateDadosUtils.isNullOrStringVazia(inscricaoEstadual)) {
      throw new NegocioException(DadosPessoaJuridicaValidationMessages.INSCRICAO_ESTADUAL_OBRIGATORIA);
    }
  }


}
