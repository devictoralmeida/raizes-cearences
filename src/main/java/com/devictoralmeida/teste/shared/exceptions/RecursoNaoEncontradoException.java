package com.devictoralmeida.teste.shared.exceptions;

import java.io.Serial;

public class RecursoNaoEncontradoException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = -8803308046217866527L;

  public RecursoNaoEncontradoException(String message) {
    super(message);
  }
}
