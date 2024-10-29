package com.devictoralmeida.teste.shared.constants.validation;

public class DadosPessoaFisicaValidationMessages {
  public static final String RG_OBRIGATORIO = "O RG é obrigatório!";
  public static final String RG_APENAS_NUMEROS = "O RG deve conter apenas números";
  public static final String RG_TAMANHO = "O RG deve ter no máximo 20 caracteres";
  public static final String NOME_OBRIGATORIO = "O nome é obrigatório!";
  public static final String NOME_TAMANHO = "O nome deve ter no máximo 100 caracteres";
  public static final String SOBRENOME_OBRIGATORIO = "O sobrenome é obrigatório!";
  public static final String SOBRENOME_TAMANHO = "O sobrenome deve ter no máximo 100 caracteres";
  public static final String ORG_EXPEDIDOR_TAMANHO = "O órgão expedidor deve ter no máximo 15 caracteres";
  public static final String DATA_EXPEDICAO_PASSADO_PRESENTE = "A data de expedição deve ser no passado ou presente";
  public static final String DATA_NASCIMENTO_OBRIGATORIA = "A data de nascimento é obrigatória!";
  public static final String DATA_NASCIMENTO_PASSADO = "A data de nascimento deve ser posterior a 01/01/1900";
  public static final String SEXO_OBRIGATORIO = "O sexo é obrigatório";
  public static final String ORG_EXPEDIDOR_APENAS_LETRAS = "O órgão expedidor deve conter apenas letras";
  public static final String DATA_EXPEDICAO_PASSADO = "A data de expedição deve ser posterior a 01/01/1900";
  public static final String DATA_EXPEDICAO_ANTERIOR_NASCIMENTO = "A data de expedição deve ser posterior à data de nascimento";

  private DadosPessoaFisicaValidationMessages() {
  }
}
