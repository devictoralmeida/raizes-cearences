package com.devictoralmeida.teste.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
public class RefreshTokenRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 8382098202468358460L;

  private final String grant_type = "refresh_token";

  @NotBlank(message = "O campo 'refresh_token' é obrigatório")
  private final String refresh_token;

  public RefreshTokenRequestDto(String refresh_token) {
    this.refresh_token = refresh_token;
  }
}
