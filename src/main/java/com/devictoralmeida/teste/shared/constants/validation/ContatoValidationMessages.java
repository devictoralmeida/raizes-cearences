package com.devictoralmeida.teste.shared.constants.validation;

public class ContatoValidationMessages {
  public static final String PREFERENCIA_CONTATO_OBRIGATORIA = "A preferência de contato é obrigatória!";
  public static final String CONTATO_OBRIGATORIO = "É obrigatório informar ao menos um contato";
  public static final String NUMERO_CONTATO_OBRIGATORIO = "O número de contato é obrigatório";
  public static final String CONTATO_TAMANHO = "O número de contato deve ter 11 caracteres";
  public static final String CONTATO_APENAS_NUMEROS = "O número de contato deve conter apenas números";
  public static final String WHATSAPP_APENAS_NUMEROS = "O número de Whatsapp deve conter apenas números";
  public static final String EMAIL_INVALIDO = "O e-mail informado é inválido!";
  public static final String EMAIL_OBRIGATORIO = "O e-mail é obrigatório!";
  public static final String CONTATO_PREFERENCIA_CONTATO_DIVERGENTE = "Conforme a preferencia de contato selecionada, o número de Whatsapp é obrigatório!";
  public static final String CONTATO_WHATSAPP_OBRIGATORIO = "O número de Whatsapp é obrigatório!";
  public static final String CONTATO_DIVERGENTE = "É permitido informar apenas um contato";
  public static final String EMAIL_TAMANHO = "O e-mail deve ter no máximo 64 caracteres antes do @ e no máximo 255 caracteres após o @";

  private ContatoValidationMessages() {
  }
}
