package com.devictoralmeida.teste.dto.request;

import com.devictoralmeida.teste.shared.constants.validation.UsuarioValidationMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
public class RecuperarSenhaRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = -5140952734522230653L;

  @NotBlank(message = UsuarioValidationMessages.LOGIN_OBRIGATORIO)
  private String login;
}
