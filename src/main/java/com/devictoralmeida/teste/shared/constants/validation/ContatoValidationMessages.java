package com.devictoralmeida.teste.shared.constants.validation;

public class ContatoValidationMessages {
  public static final String PREFERENCIA_CONTATO_OBRIGATORIA = "A preferência de contato é obrigatória!";
  public static final String CONTATO_OBRIGATORIO = "É obrigatório informar ao menos um contato";
  public static final String CONTATO_TAMANHO = "O número de contato deve ter 11 caracteres";
  public static final String CONTATO_APENAS_NUMEROS = "O número de contato deve conter apenas números";
  public static final String WHATSAPP_APENAS_NUMEROS = "O número de Whatsapp deve conter apenas números";
  public static final String EMAIL_INVALIDO = "O e-mail informado é inválido!";
  public static final String EMAIL_TAMANHO = "O e-mail deve ter no máximo 320 caracteres";
  public static final String EMAIL_OBRIGATORIO = "Conforme a preferencia de contato selecionada, o e-mail é obrigatório!";
  public static final String EMAIL_JA_CADASTRADO = "O e-mail informado já está cadastrado no sistema";
  public static final String CONTATO_PREFERENCIA_CONTATO_DIVERGENTE = "Conforme a preferencia de contato selecionada, o número de Whatsapp é obrigatório!";
  public static final String CONTATO_WHATSAPP_OBRIGATORIO = "O número de Whatsapp é obrigatório!";
  public static final String WHATSAPP_JA_CADASTRADO = "O número de Whatsapp informado já está cadastrado no sistema";

  private ContatoValidationMessages() {
  }
}
