package com.devictoralmeida.teste.dto.request;

import com.devictoralmeida.teste.enums.GrauInstrucao;
import com.devictoralmeida.teste.enums.Sexo;
import com.devictoralmeida.teste.shared.constants.SharedConstants;
import com.devictoralmeida.teste.shared.constants.validation.DadosPessoaFisicaValidationMessages;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class DadosPessoaFisicaRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 7406485929631979628L;

  private UUID id;

  @NotBlank(message = DadosPessoaFisicaValidationMessages.NOME_OBRIGATORIO)
  @Size(max = 100, message = DadosPessoaFisicaValidationMessages.NOME_TAMANHO)
  private String nome;

  @NotBlank(message = DadosPessoaFisicaValidationMessages.SOBRENOME_OBRIGATORIO)
  @Size(max = 100, message = DadosPessoaFisicaValidationMessages.SOBRENOME_TAMANHO)
  private String sobrenome;

  @NotBlank(message = DadosPessoaFisicaValidationMessages.RG_OBRIGATORIO)
  @Pattern(regexp = SharedConstants.REGEX_APENAS_NUMEROS, message = DadosPessoaFisicaValidationMessages.RG_APENAS_NUMEROS)
  @Size(max = SharedConstants.TAMANHO_MAXIMO_RG, message = DadosPessoaFisicaValidationMessages.RG_TAMANHO)
  private String rg;

  @Pattern(regexp = SharedConstants.REGEX_APENAS_LETRAS, message = DadosPessoaFisicaValidationMessages.ORG_EXPEDIDOR_APENAS_LETRAS)
  @Size(max = 15, message = DadosPessoaFisicaValidationMessages.ORG_EXPEDIDOR_TAMANHO)
  private String orgaoExpeditor;

  @PastOrPresent(message = DadosPessoaFisicaValidationMessages.DATA_EXPEDICAO_PASSADO_PRESENTE)
  private LocalDate dataExpedicao;

  @NotNull(message = DadosPessoaFisicaValidationMessages.DATA_NASCIMENTO_OBRIGATORIA)
  private LocalDate dataNascimento;

  @NotNull(message = DadosPessoaFisicaValidationMessages.SEXO_OBRIGATORIO)
  private Sexo sexo;

  private GrauInstrucao grauInstrucao;

  void validar() {
    validarDatas();
  }

  private void validarDatas() {
    if (dataNascimento.isBefore(SharedConstants.DATA_MINIMA)) {
      throw new NegocioException(DadosPessoaFisicaValidationMessages.DATA_NASCIMENTO_PASSADO);
    }

    if (dataExpedicao != null && dataExpedicao.isBefore(SharedConstants.DATA_MINIMA)) {
      throw new NegocioException(DadosPessoaFisicaValidationMessages.DATA_EXPEDICAO_PASSADO);
    }
  }
}
