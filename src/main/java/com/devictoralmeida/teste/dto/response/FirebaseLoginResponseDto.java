package com.devictoralmeida.teste.dto.response;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
public class FirebaseLoginResponseDto implements Serializable {
  @Serial
  private static final long serialVersionUID = -1857780050295696126L;

  private String email;
  private String displayName;
  private String idToken;
  private String refreshToken;
  private String expiresIn;
}
