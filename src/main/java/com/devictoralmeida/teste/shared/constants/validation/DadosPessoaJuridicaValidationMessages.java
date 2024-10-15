package com.devictoralmeida.teste.shared.constants.validation;

public class DadosPessoaJuridicaValidationMessages {
  public static final String RAZAO_SOCIAL_OBRIGATORIA = "A razão social é obrigatória!";
  public static final String RAZAO_SOCIAL_TAMANHO = "A razão social deve ter no máximo 100 caracteres";
  public static final String NOME_FANTASIA_TAMANHO = "O nome fantasia deve ter no máximo 100 caracteres";
  public static final String SIGLA_OBRIGATORIA = "A sigla é obrigatória!";
  public static final String SIGLA_TAMANHO = "A sigla deve ter no máximo 150 caracteres";
  public static final String NOME_FANTASIA_OBRIGATORIO = "O nome fantasia é obrigatório!";
  public static final String DATA_FUNDACAO_PASSADO_PRESENTE = "A data de fundação deve ser no passado";
  public static final String DATA_FUNDACAO_TAMANHO = "A data de fundação deve ter 8 caracteres";
  public static final String DATA_FUNDACAO_OBRIGATORIA = "A data de fundação é obrigatória";
  public static final String INSCRICAO_JUNTA_COMERCIAL_OBRIGATORIA = "A inscrição na junta comercial é obrigatória";
  public static final String INSCRICAO_JUNTA_COMERCIAL_TAMANHO = "A inscrição na junta comercial deve ter no máximo 50 caracteres";
  public static final String INSCRICAO_JUNTA_COMERCIAL_APENAS_NUMEROS = "A inscrição na junta comercial deve conter apenas números";
  public static final String INSCRICAO_ESTADUAL_TAMANHO = "A inscrição estadual deve ter no máximo 12 caracteres";
  public static final String INSCRICAO_ESTADUAL_OBRIGATORIA = "A inscrição estadual é obrigatória";

  private DadosPessoaJuridicaValidationMessages() {
  }
}
