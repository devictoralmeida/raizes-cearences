package com.devictoralmeida.teste.dto.request;

import com.devictoralmeida.teste.shared.constants.validation.UsuarioValidationMessages;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class CodigoRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 7256937663879706028L;

  @NotEmpty(message = UsuarioValidationMessages.CODIGO_OBRIGATORIO)
  private String codigo;
}
