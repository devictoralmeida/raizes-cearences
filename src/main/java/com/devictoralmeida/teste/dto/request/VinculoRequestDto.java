package com.devictoralmeida.teste.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
  private boolean isCadastroSecaf = false;

  @JsonProperty("isServicosAter")
  private boolean isServicosAter = false;

  @JsonProperty("isOfertaCeasa")
  private boolean isOfertaCeasa = false;
}
