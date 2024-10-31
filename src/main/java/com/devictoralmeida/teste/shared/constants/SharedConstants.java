package com.devictoralmeida.teste.shared.constants;

import java.time.LocalDate;
import java.util.List;

public class SharedConstants {
  public static final String PERMISSOES = "permissoes";
  public static final String REGEX_APENAS_NUMEROS = "^\\d+$";
  public static final String REGEX_ALFANUMERICO = "^(?! )[a-zA-Z0-9 ]*(?<! )$";
  public static final String PREFIX_TELEFONE_BR = "+55";
  public static final String EMAIL_DOMINIO_RAIZES = "@raizes-cearences.com.br";

  public static final int TAMANHO_PADRAO_NOME_RAZAO_SOCIAL = 100;
  public static final int TAMANHO_MAXIMO_DOMINIO_EMAIL = 255;
  public static final int TAMANHO_MAXIMO_LOCAL_EMAIL = 64;

  public static final int TAMANHO_CONTATO_NUMERICO = 11;
  public static final int TAMANHO_CPF = 11;
  public static final int TAMANHO_MAXIMO_RG = 20;
  public static final int TAMANHO_CNPJ = 14;

  public static final LocalDate DATA_MINIMA = LocalDate.of(1900, 1, 1);

  public static final List<String> FORMATOS_PERMITIDOS_ANEXO = List.of("pdf");

  public static final String REGEX_APENAS_LETRAS = "^[a-zA-ZÀ-ÖØ-öø-ÿ\\s]+$";
  public static final String CONTEM_APENAS_LETRAS_NUMEROS = "[\\p{L}0-9\\s-.,]+";
  public static final String REGEX_VALIDA_SENHA = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\W)[A-Za-z\\d\\W]{8,}$";

  private SharedConstants() {
  }
}
