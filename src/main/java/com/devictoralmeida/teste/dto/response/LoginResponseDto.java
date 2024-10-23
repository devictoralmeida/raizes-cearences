package com.devictoralmeida.teste.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto implements Serializable {
  @Serial
  private static final long serialVersionUID = -1857780050295696126L;

  private String idToken;
  private String refreshToken;
  private String expiresIn;
}
