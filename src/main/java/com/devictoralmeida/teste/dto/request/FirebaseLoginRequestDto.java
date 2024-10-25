package com.devictoralmeida.teste.dto.request;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
public class FirebaseLoginRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 8382098202468358460L;

  private final String email;
  private final String password;
  private final boolean returnSecureToken = true;

  public FirebaseLoginRequestDto(String email, String password) {
    this.email = email;
    this.password = password;
  }
}
