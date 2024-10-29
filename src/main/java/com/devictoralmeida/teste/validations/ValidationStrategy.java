package com.devictoralmeida.teste.validations;

public interface ValidationStrategy<T> {
  void validar(T dto);
}
