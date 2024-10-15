package com.devictoralmeida.teste.shared.constants.validation;

public class VinculoValidationMessages {
  public static final String CAF_TAMANHO = "O CAF deve ter no máximo 11 caracteres";
  public static final String CAF_JURIDICA_INVALIDA = "O CAF jurídico só pode ser informado para cooperativas ou associações";
  public static final String CAF_JURIDICA_TAMANHO = "O CAF jurídico deve ter 11 caracteres";
  public static final String CAF_JURIDICA_APENAS_NUMEROS = "O CAF jurídico deve conter apenas números";
  public static final String DATA_VALIDADE_CAF_FUTURA = "A data de validade do CAF deve ser futura ou presente";
  public static final String DATA_VALIDADE_CAF_TAMANHO = "A data de validade do CAF deve ter 8 caracteres";
  public static final String CADASTRO_SECAF_OBRIGATORIO = "Informe se possui cadastro no SECAF";
  public static final String SERVICOS_ATER_OBRIGATORIO = "Informe se recebe serviços de ATER";
  public static final String REGISTRO_SANITARIO_OBRIGATORIO = "O registro sanitário é obrigatório quando informado que possui registro sanitário";

  private VinculoValidationMessages() {
  }
}
