package com.devictoralmeida.teste.shared.constants;

public class RotasPermitidasConstants {
  public static final String[] ROTAS_PERMITIDAS = {
          "/auth/**",
          "/usuario/criar-senha/**",
          "/usuario/upload/**",
          "/usuario/reenviar-codigo/**",
          "/usuario/validacao-codigo/**",
          "/usuario/alterar-contato/**",
          "/swagger-ui/**",
          "/v*/api-docs/**",
          "/swagger-resources/**"
  };

  private RotasPermitidasConstants() {
  }
}
