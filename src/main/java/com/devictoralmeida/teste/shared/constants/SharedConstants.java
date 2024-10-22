package com.devictoralmeida.teste.shared.constants;

public class SharedConstants {
  public static final String PERMISSOES = "permissoes";
  public static final String REGEX_APENAS_NUMEROS = "^\\d+$";
  public static final String REGEX_ALFANUMERICO = "^[a-zA-Z0-9]+$";

  public static final int TAMANHO_CONTATO_NUMERICO = 11;

  public static final int TAMANHO_CPF = 11;
  public static final int TAMANHO_MAXIMO_RG = 20;
  public static final int TAMANHO_CNPJ = 14;

  public static final int TAMANHO_KB = 1024;

  public static final int TAMANHO_MAXIMO_UPLOAD_ARQUIVO = 10;

  public static final int TEMPO_EXPIRACAO_CODIGO_VERIFICACAO = 900000;

  public static final String REGEX_DATA_DIA_MES_ANO = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";

  public static final String REGEX_APENAS_LETRAS = "^[a-zA-ZÀ-ÖØ-öø-ÿ\\s]+$";

  public static final String CONTEM_APENAS_LETRAS_NUMEROS = "[\\p{L}0-9\\s-.,]+";
  public static final String REGEX_VALIDA_SENHA = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\W)[A-Za-z\\d\\W]{8,}$";

  private SharedConstants() {
  }
}
