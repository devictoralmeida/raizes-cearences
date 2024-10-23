package com.devictoralmeida.teste.dto.request;

import com.devictoralmeida.teste.shared.constants.SharedConstants;
import com.devictoralmeida.teste.shared.constants.validation.PresidenteValidationMessages;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class PresidenteRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 1728741167053384621L;

  @NotBlank(message = PresidenteValidationMessages.DOCUMENTO_OBRIGATORIO)
  @Pattern(regexp = SharedConstants.REGEX_APENAS_NUMEROS, message = PresidenteValidationMessages.DOCUMENTO_APENAS_NUMEROS)
  @Size(min = SharedConstants.TAMANHO_CPF, max = SharedConstants.TAMANHO_CPF, message = PresidenteValidationMessages.DOCUMENTO_TAMANHO)
  private String documento;

  @NotNull(message = PresidenteValidationMessages.DADOS_PESSOAIS_OBRIGATORIO)
  private @Valid DadosPessoaFisicaRequestDto dadosPessoais;

  @NotNull(message = PresidenteValidationMessages.CONTATO_OBRIGATORIO)
  private @Valid ContatoRequestDto contato;

  @NotNull(message = PresidenteValidationMessages.DATA_INICIO_MANDATO_OBRIGATORIA)
  @PastOrPresent(message = PresidenteValidationMessages.DATA_INICIO_MANDATO_PASSADO_OU_PRESENTE)
  private LocalDate dataInicioMandato;

  @NotNull(message = PresidenteValidationMessages.DATA_FINAL_MANDATO_OBRIGATORIA)
  private LocalDate dataFinalMandato;

  public void validar() {
    validarDatasMandato();
    dadosPessoais.validar();
    contato.validacoesPresidente();
  }

  private void validarDatasMandato() {
    if (dataInicioMandato.isBefore(SharedConstants.DATA_MINIMA)) {
      throw new NegocioException(PresidenteValidationMessages.DATA_INICIO_MANDATO_PASSADO);
    }

    if (dataFinalMandato.isBefore(SharedConstants.DATA_MINIMA)) {
      throw new NegocioException(PresidenteValidationMessages.DATA_FINAL_MANDATO_PASSADO);
    }
  }
}
