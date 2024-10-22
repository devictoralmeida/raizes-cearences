package com.devictoralmeida.teste.dto.request;

import com.devictoralmeida.teste.shared.constants.validation.UsuarioValidationMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class LoginRequestDto implements Serializable {

  @NotBlank(message = UsuarioValidationMessages.LOGIN_OBRIGATORIO)
  private String login;

  @NotBlank(message = UsuarioValidationMessages.SENHA_OBRIGATORIA)
  private String senha;
}
