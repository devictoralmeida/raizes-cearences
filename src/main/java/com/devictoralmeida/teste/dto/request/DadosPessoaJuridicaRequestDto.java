package com.devictoralmeida.teste.dto.request;

import com.devictoralmeida.teste.shared.constants.SharedConstants;
import com.devictoralmeida.teste.shared.constants.validation.DadosPessoaJuridicaValidationMessages;
import com.devictoralmeida.teste.validations.CooperativaAssociacaoValidationStrategy;
import com.devictoralmeida.teste.validations.impl.DadosPessoaJuridicaRequestValidationStrategyImpl;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class DadosPessoaJuridicaRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 9132679813191593067L;


  private CooperativaAssociacaoValidationStrategy<DadosPessoaJuridicaRequestDto> validationStrategy;
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
  @PastOrPresent(message = DadosPessoaJuridicaValidationMessages.DATA_FUNDACAO_PASSADO_PRESENTE)
  private LocalDate dataFundacao;
  @Size(max = 11, message = DadosPessoaJuridicaValidationMessages.CAF_TAMANHO)
  private String caf;
  private LocalDate dataValidadeCaf;

  public DadosPessoaJuridicaRequestDto() {
    validationStrategy = new DadosPessoaJuridicaRequestValidationStrategyImpl();
  }

  public void validar() {
    validationStrategy.validar(this);
  }


  public void validarCooperativaAssociacao() {
    validationStrategy.validarCooperativaAssociacao(this);
  }
}