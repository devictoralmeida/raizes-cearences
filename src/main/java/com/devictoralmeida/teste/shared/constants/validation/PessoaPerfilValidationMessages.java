package com.devictoralmeida.teste.shared.constants.validation;

public class PessoaPerfilValidationMessages {
  public static final String TIPO_USUARIO_OBRIGATORIO = "O tipo de usuário é obrigatório!";
  public static final String DOCUMENTO_OBRIGATORIO = "O documento é obrigatório!";
  public static final String DOCUMENTO_APENAS_NUMEROS = "O documento deve conter apenas números";
  public static final String DOCUMENTO_TAMANHO = "O documento deve ter 11 ou 14 caracteres";
  public static final String VINCULO_OBRIGATORIO = "O vínculo é obrigatório!";
  public static final String CONTATO_OBRIGATORIO = "O contato é obrigatório!";
  public static final String ENDERECO_OBRIGATORIO = "O endereço é obrigatório!";
  public static final String DADOS_PESSOAIS_OBRIGATORIOS = "É obrigatório informar os dados pessoais";
  public static final String DOCUMENTO_CPF_TAMANHO = "O documento de pessoa física deve ter 11 caracteres";
  public static final String DOCUMENTO_CNPJ_TAMANHO = "O documento de pessoa jurídica deve ter 14 caracteres";

  public static final String DADOS_PESSOA_FISICA_OBRIGATORIO = "É obrigatório informar os dados da pessoa física";
  public static final String DADOS_PESSOA_JURIDICA_OBRIGATORIO = "É obrigatório informar os dados da pessoa jurídica";
  public static final String PRESIDENTE_OBRIGATORIO = "É obrigatório informar os dados do presidente";
  public static final String PRESIDENTE_NAO_PERMITIDO = "O presidente não é permitido para este tipo de perfil";
  public static final String PESSOA_FISICA_NAO_PERMITIDA = "Pessoa física não é permitida para este tipo de perfil";
  public static final String DADOS_PESSOAIS_EXCLUSIVOS = "É permitido informar apenas os dados da pessoa física ou os dados da pessoa jurídica";

  private PessoaPerfilValidationMessages() {
  }
}
