package com.devictoralmeida.teste.shared.constants.validation;

public class PresidenteValidationMessages {
  public static final String DOCUMENTO_OBRIGATORIO = "O documento é obrigatório!";
  public static final String DADOS_PESSOAIS_OBRIGATORIO = "Os dados pessoais são obrigatórios!";
  public static final String CONTATO_OBRIGATORIO = "O contato é obrigatório!";
  public static final String ENDERECO_OBRIGATORIO = "O endereço é obrigatório!";
  public static final String DOCUMENTO_APENAS_NUMEROS = "O documento deve conter apenas números";
  public static final String DOCUMENTO_TAMANHO = "O documento do presidente deve ter 11 caracteres";
  public static final String DATA_INICIO_MANDATO_OBRIGATORIA = "A data de início do mandato é obrigatória!";
  public static final String DATA_INICIO_MANDATO_PASSADO_OU_PRESENTE = "A data de início do mandato deve ser no passado ou presente!";
  public static final String DATA_FINAL_MANDATO_OBRIGATORIA = "A data de final do mandato é obrigatória!";
  public static final String DATA_FINAL_MANDATO_FUTURO_OU_PRESENTE = "A data de final do mandato deve ser no futuro ou presente!";
  public static final String DATA_INICIO_MANDATO_PASSADO = "A data de início do mandato deve ser posterior a 01/01/1900";
  public static final String DATA_FINAL_MANDATO_PASSADO = "A data de final do mandato deve ser posterior a 01/01/1900";

  private PresidenteValidationMessages() {
  }
}
