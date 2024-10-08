package com.devictoralmeida.teste.shared.constants.validation;

public class AnexoValidationMessages {
  public static final String TIPO_MIME_TAMANHO = "O tipo mime do anexo deve ter no máximo 5 caracteres";
  public static final String TIPO_MIME_OBRIGATORIO = "O tipo mime do anexo é obrigatório";
  public static final String NOME_ANEXO_TAMANHO = "O nome do anexo deve ter no máximo 255 caracteres";
  public static final String TIPO_DOCUMENTO_OBRIGATORIO = "O tipo de documento do anexo é obrigatório";
  public static final String TIPO_INVALIDO = "O tipo de arquivo enviado não é permitido, os tipos permitidos são: pdf, png, doc, docx e odt";
  public static final String ATA_ASSEMBLEIA_GERAL_OBRIGATORIA = "O anexo de ata de assembléia geral é obrigatório";
  public static final String ATA_FUNDACAO_OBRIGATORIA = "O anexo de ata de fundação é obrigatório";
  public static final String ATA_ULTIMA_ELEICAO_OBRIGATORIA = "O anexo de ata de última eleição é obrigatório";
  public static final String ESTATUTO_OBRIGATORIO = "O anexo de estatuto é obrigatório";
  public static final String TIPO_PERFIL_INVALIDO = "Conforme o tipo de perfil informado, não é possível enviar anexos";
  public static final String ARQUIVO_OBRIGATORIO = "O arquivo do anexo é obrigatório";

  private AnexoValidationMessages() {
  }
}
