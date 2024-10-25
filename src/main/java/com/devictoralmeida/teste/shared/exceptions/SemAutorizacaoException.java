package com.devictoralmeida.teste.shared.exceptions;

import java.io.Serial;

public class SemAutorizacaoException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 1L;

  public SemAutorizacaoException(String message) {
    super(message);
  }
}
