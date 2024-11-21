package com.devictoralmeida.teste.shared.exceptions;

import java.io.Serial;

public class SemAutenticacaoException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 1L;

  public SemAutenticacaoException(String message) {
    super(message);
  }
}
