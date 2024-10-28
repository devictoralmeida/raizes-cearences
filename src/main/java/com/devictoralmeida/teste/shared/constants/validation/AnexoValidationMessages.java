package com.devictoralmeida.teste.shared.constants.validation;

public class AnexoValidationMessages {
  public static final String TIPO_MIME_TAMANHO = "O tipo mime do anexo deve ter no máximo 5 caracteres";
  public static final String NOME_ANEXO_TAMANHO = "O nome do anexo deve ter no máximo 255 caracteres";
  public static final String TIPO_DOCUMENTO_OBRIGATORIO = "O tipo de documento do anexo é obrigatório";
  public static final String TIPO_INVALIDO = "O tipo de arquivo enviado não é permitido, o tipo permitido é: pdf";
  public static final String ATA_ASSEMBLEIA_GERAL_OBRIGATORIA = "O anexo de ata de assembléia geral é obrigatório";
  public static final String ATA_FUNDACAO_OBRIGATORIA = "O anexo de ata de fundação é obrigatório";
  public static final String ATA_ULTIMA_ELEICAO_OBRIGATORIA = "O anexo de ata de última eleição é obrigatório";
  public static final String ESTATUTO_OBRIGATORIO = "O anexo de estatuto é obrigatório";
  public static final String TIPO_PERFIL_INVALIDO = "Conforme o tipo de perfil informado, não é possível enviar anexos";
  public static final String ARQUIVO_OBRIGATORIO = "O arquivo do anexo é obrigatório";
  public static final String ARQUIVO_TAMANHO_MAXIMO = "O arquivo devem ter no máximo 10MB";
  public static final String QUANTIDADE_TIPO_DOCUMENTO_ARQUIVO_DIFERENTE = "A quantidade de tipos de documento e arquivos enviados é diferente";
  public static final String ATA_ASSEMBLEIA_GERAL_NAO_PERMITIDA = "Conforme o tipo de perfil do usuário, não é permitido enviar o anexo de Ata da Assembléia Geral.";

  private AnexoValidationMessages() {
  }
}
