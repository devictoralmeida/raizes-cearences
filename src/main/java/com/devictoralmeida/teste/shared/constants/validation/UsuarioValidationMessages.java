package com.devictoralmeida.teste.shared.constants.validation;

public class UsuarioValidationMessages {
  public static final String TIPO_PERFIL_OBRIGATORIO = "O tipo de perfil do usuário é obrigatório";
  public static final String PESSOA_PERFIL_OBRIGATORIA = "O perfil da pessoa é obrigatório";
  public static final String LOGIN_OBRIGATORIO = "O login é obrigatório";
  public static final String CODIGO_OBRIGATORIO = "O código é obrigatório";
  public static final String SENHA_OBRIGATORIA = "A senha é obrigatória";
  public static final String NOVA_SENHA_OBRIGATORIA = "A nova senha é obrigatória";
  public static final String SENHA_ATUAL_OBRIGATORIA = "A senha atual é obrigatória";
  public static final String SENHA_TAMANHO = "A senha deve ter entre 8 e 32 caracteres";
  public static final String ALTERAR_SENHA_TAMANHO = "Senha incorreta. A nova senha deverá conter entre 8 e 32 caracteres";
  public static final String SENHA_INVALIDA = "Para criar uma senha mais segura, use números, letras maiúsculas, letras minúsculas e caracteres especiais.";
  public static final String SENHA_DIFERENTE = "As senhas não correspondem. Utilize a mesma senha";
  public static final String CONFIRMACAO_SENHA_OBRIGATORIA = "A confirmação da senha é obrigatória";
  public static final String CONFIRMAR_NOVA_SENHA_OBRIGATORIA = "A confirmação da nova senha é obrigatória";
  public static final String SENHA_NOVA_IGUAL_ATUAL = "A senha atual e a nova senha devem ser diferentes!";
  public static final String LOGIN_APENAS_NUMEROS = "O login deve conter apenas números";


  private UsuarioValidationMessages() {
  }
}
