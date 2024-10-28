package com.devictoralmeida.teste.shared.utils;

import com.devictoralmeida.teste.shared.constants.SharedConstants;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class ValidarDadosUtils {

  private ValidarDadosUtils() {
  }

  public static boolean contemApenasLetras(String texto) {
    return Pattern.matches(SharedConstants.REGEX_APENAS_LETRAS, texto);
  }

  public static boolean contemApenasNumeros(String texto) {
    return Pattern.matches(SharedConstants.REGEX_APENAS_NUMEROS, texto);
  }

  public static boolean contemApenasLetrasEnumero(String texto) {
    return Pattern.matches(SharedConstants.CONTEM_APENAS_LETRAS_NUMEROS, texto);
  }

  public static boolean isNullOrVazio(List<?> lista) {
    return Objects.isNull(lista) || lista.isEmpty();
  }

  public static boolean isNullOrStringVazia(String valor) {
    return Objects.isNull(valor) || valor.isEmpty();
  }
}
