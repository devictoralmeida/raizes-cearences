package com.devictoralmeida.teste.dto.request;

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
public class FirebaseAuthRequestDto implements Serializable {

  @Serial
  private static final long serialVersionUID = -6458806298345818807L;
  private final boolean returnSecureToken = true;
  private String token;
}
