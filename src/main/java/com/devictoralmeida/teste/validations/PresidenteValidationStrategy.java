package com.devictoralmeida.teste.validations;

public interface PresidenteValidationStrategy<T> extends ValidationStrategy<T> {
  void validarPresidente(T dto);
}
