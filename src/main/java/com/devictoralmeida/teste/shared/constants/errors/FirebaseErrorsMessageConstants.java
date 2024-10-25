package com.devictoralmeida.teste.shared.constants.errors;

public class FirebaseErrorsMessageConstants {
  public static final String ERRO_CRIAR_USUARIO = "Erro ao criar o usuário no Firebase";
  public static final String ERRO_ATUALIZAR_CONTATO_USUARIO = "Erro ao atualizar o contato do usuário no Firebase";
  public static final String ERRO_USUARIO_NAO_ENCONTRADO = "Usuário não encontrado no Firebase";
  public static final String ERRO_VERIFICACAO_EMAIL = "Erro ao verificar o email do usuário no Firebase";
  public static final String ERRO_DELETAR_USUARIO = "Erro ao deletar o usuário no Firebase";
  public static final String ERRO_AUTENTICACAO_TOKEN = "Falha de autenticação: Token ausente, inválido ou expirado";
  public static final String INVALID_CREDENTIALS_ERROR = "INVALID_CREDENTIALS";
  public static final String INVALID_REFRESH_TOKEN_ERROR = "INVALID_REFRESH_TOKEN";
  public static final String ERRO_REFRESH_TOKEN_INVALIDO = "Refresh token inválido!";
  public static final String ERRO_CREDENCIAIS_INVALIDAS = "Login e/ou Senha incorretos. Verifique suas credenciais e tente novamente";

  private FirebaseErrorsMessageConstants() {
  }
}
