package com.devictoralmeida.teste.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class RefreshTokenResponseDto implements Serializable {
  @Serial
  private static final long serialVersionUID = -1857780050295696126L;

  private String id_token;
}
