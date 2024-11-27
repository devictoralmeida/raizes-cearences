package com.devictoralmeida.teste.shared.constants.errors;

public class AuthErrorsMessageConstants {
  public static final String ERRO_ACEITE_TERMOS = "O Usuário Você aceitar os termos e condições de uso para continuar a utilizar o sistema";
  public static final String ERRO_AUTENTICACAO_TOKEN = "Falha de autenticação: Token ausente, inválido ou expirado";
  public static final String INVALID_CREDENTIALS_ERROR = "INVALID_LOGIN_CREDENTIALS";
  public static final String INVALID_REFRESH_TOKEN_ERROR = "INVALID_REFRESH_TOKEN";
  public static final String ERRO_REFRESH_TOKEN_INVALIDO = "Refresh token inválido!";
  public static final String ERRO_CREDENCIAIS_INVALIDAS = "Login e/ou Senha incorretos. Verifique e tente novamente";
  public static final String ERRO_SEM_PERMISSAO = "Usuário não possui permissão para realizar esta operação";

  private AuthErrorsMessageConstants() {
  }
}
