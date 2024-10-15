package com.devictoralmeida.teste.dto.request;

import com.devictoralmeida.teste.enums.RegistroSanitario;
import com.devictoralmeida.teste.shared.constants.SharedConstants;
import com.devictoralmeida.teste.shared.constants.validation.VinculoValidationMessages;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import com.devictoralmeida.teste.shared.utils.ValidateDadosUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class VinculoRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 1525186355380588415L;

  @Size(max = 11, message = VinculoValidationMessages.CAF_TAMANHO)
  private String caf;

  @JsonProperty("isCafJuridica")
  private boolean isCafJuridica;

  @Pattern(regexp = SharedConstants.REGEX_APENAS_NUMEROS, message = VinculoValidationMessages.CAF_JURIDICA_APENAS_NUMEROS)
  @Size(min = 11, max = 11, message = VinculoValidationMessages.CAF_JURIDICA_TAMANHO)
  private String cafJuridica;

  @FutureOrPresent(message = VinculoValidationMessages.DATA_VALIDADE_CAF_FUTURA)
  private LocalDate dataValidadeCaf;

  @JsonProperty("isCadastroSecaf")
  @NotNull(message = VinculoValidationMessages.CADASTRO_SECAF_OBRIGATORIO)
  private boolean isCadastroSecaf;

  @JsonProperty("isServicosAter")
  @NotNull(message = VinculoValidationMessages.SERVICOS_ATER_OBRIGATORIO)
  private boolean isServicosAter;

  @JsonProperty("isOfertaCeasa")
  private boolean isOfertaCeasa;

  @JsonProperty("isRegistroSanitario")
  private boolean isRegistroSanitario;

  @JsonProperty("isAssistenciaTecnica")
  private boolean isAssistenciaTecnica;

  private RegistroSanitario registroSanitario;

  public void validar() {
    validar(false);
  }

  public void validar(boolean isCooperativaAssociacao) {
    validarRegistroSanitario();
    boolean enviouDadosCafJuridica = isCafJuridica || !ValidateDadosUtils.isNullOrStringVazia(cafJuridica);

    if (!isCooperativaAssociacao && enviouDadosCafJuridica) {
      throw new NegocioException(VinculoValidationMessages.CAF_JURIDICA_INVALIDA);
    }
  }

  private void validarRegistroSanitario() {
    if (isRegistroSanitario && registroSanitario == null) {
      throw new NegocioException(VinculoValidationMessages.REGISTRO_SANITARIO_OBRIGATORIO);
    }
  }
}
