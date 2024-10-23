package com.devictoralmeida.teste.dto.request;

import com.devictoralmeida.teste.shared.constants.validation.VinculoValidationMessages;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class VinculoRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 1525186355380588415L;

  @JsonProperty("isCadastroSecaf")
  @NotNull(message = VinculoValidationMessages.CADASTRO_SECAF_OBRIGATORIO)
  private boolean isCadastroSecaf;

  @JsonProperty("isServicosAter")
  @NotNull(message = VinculoValidationMessages.SERVICOS_ATER_OBRIGATORIO)
  private boolean isServicosAter;

  @JsonProperty("isOfertaCeasa")
  @NotNull(message = VinculoValidationMessages.SERVICOS_OFERTA_CEASA_OBRIGATORIA)
  private boolean isOfertaCeasa;
}
