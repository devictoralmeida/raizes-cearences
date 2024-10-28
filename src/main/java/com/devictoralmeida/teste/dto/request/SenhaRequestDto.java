package com.devictoralmeida.teste.dto.request;

import com.devictoralmeida.teste.shared.constants.SharedConstants;
import com.devictoralmeida.teste.shared.constants.validation.UsuarioValidationMessages;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class SenhaRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = -5140952734522230653L;

  @NotBlank(message = UsuarioValidationMessages.SENHA_OBRIGATORIA)
  @Size(min = 8, max = 32, message = UsuarioValidationMessages.SENHA_TAMANHO)
  @Pattern(regexp = SharedConstants.REGEX_VALIDA_SENHA, message = UsuarioValidationMessages.SENHA_INVALIDA)
  private String senha;

  @NotBlank(message = UsuarioValidationMessages.CONFIRMACAO_SENHA_OBRIGATORIA)
  private String confirmacaoSenha;


  public void validar() {
    validarSenhas();
  }

  private void validarSenhas() {
    if (!senha.equals(confirmacaoSenha)) {
      throw new NegocioException(UsuarioValidationMessages.SENHA_DIFERENTE);
    }
  }
}
