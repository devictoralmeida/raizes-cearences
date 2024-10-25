package com.devictoralmeida.teste.shared.constants.validation;

public class UsuarioValidationMessages {
  public static final String TIPO_PERFIL_OBRIGATORIO = "O tipo de perfil do usuário é obrigatório";
  public static final String PESSOA_PERFIL_OBRIGATORIA = "O perfil da pessoa é obrigatório";
  public static final String LOGIN_OBRIGATORIO = "O login é obrigatório";
  public static final String SENHA_OBRIGATORIA = "A senha é obrigatória";
  public static final String SENHA_TAMANHO = "A senha deve ter entre 8 e 32 caracteres";
  public static final String SENHA_INVALIDA = "Para criar uma senha mais segura, use números, letras maiúsculas, letras minúsculas e caracteres especiais.";
  public static final String SENHA_DIFERENTE = "As senhas não correspondem. Verifique o conteúdo digitado e tente novamente.";
  public static final String CONFIRMACAO_SENHA_OBRIGATORIA = "A confirmação da senha é obrigatória";


  private UsuarioValidationMessages() {
  }
}
