package com.devictoralmeida.teste.dto.request;

import com.devictoralmeida.teste.shared.constants.validation.UsuarioValidationMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
public class LoginRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = -721054336603288433L;

  @NotBlank(message = UsuarioValidationMessages.LOGIN_OBRIGATORIO)
  private String login;

  @NotBlank(message = UsuarioValidationMessages.SENHA_OBRIGATORIA)
  private String senha;
}
