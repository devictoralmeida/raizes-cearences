package com.devictoralmeida.teste.shared.constants.validation;

public class EnderecoValidationMessages {
  public static final String CEP_APENAS_NUMEROS = "O CEP deve conter apenas números";
  public static final String CEP_TAMANHO = "O CEP deve ter 8 caracteres";
  public static final String CEP_OBRIGATORIO = "O CEP é obrigatório!";
  public static final String MUNICIPIO_TAMANHO = "O município deve ter no máximo 100 caracteres";
  public static final String MUNICIPIO_OBRIGATORIO = "O município é obrigatório!";
  public static final String LOCALIDADE_TAMANHO = "A localidade deve ter no máximo 100 caracteres";
  public static final String LOGRADOURO_TAMANHO = "O logradouro deve ter no máximo 100 caracteres";
  public static final String NUMERO_ALFANUMERICO = "O número do endereço deve conter apenas letras e números";
  public static final String NUMERO_TAMANHO = "O número deve ter no máximo 10 caracteres";
  public static final String COMPLEMENTO_TAMANHO = "O complemento deve ter no máximo 50 caracteres";
  public static final String BAIRRO_TAMANHO = "O bairro deve ter no máximo 50 caracteres";
  public static final String UF_TAMANHO = "A UF deve ter 2 caracteres";
  public static final String UF_OBRIGATORIA = "A UF é obrigatória!";
  public static final String PONTO_REFERENCIA_TAMANHO = "O ponto de referência deve ter no máximo 50 caracteres";

  private EnderecoValidationMessages() {
  }
}
