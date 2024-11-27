package com.devictoralmeida.teste.dto.request.update;

import com.devictoralmeida.teste.shared.constants.SharedConstants;
import com.devictoralmeida.teste.shared.constants.validation.UsuarioValidationMessages;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
public class SenhaUpdateRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = -721054336603288433L;

  @NotBlank(message = UsuarioValidationMessages.LOGIN_OBRIGATORIO)
  private String login;

  @NotBlank(message = UsuarioValidationMessages.SENHA_ATUAL_OBRIGATORIA)
  private String senhaAtual;

  @NotBlank(message = UsuarioValidationMessages.NOVA_SENHA_OBRIGATORIA)
  @Size(min = 8, max = 32, message = UsuarioValidationMessages.ALTERAR_SENHA_TAMANHO)
  @Pattern(regexp = SharedConstants.REGEX_VALIDA_SENHA, message = UsuarioValidationMessages.SENHA_INVALIDA)
  private String novaSenha;

  @NotBlank(message = UsuarioValidationMessages.CONFIRMAR_NOVA_SENHA_OBRIGATORIA)
  private String confirmarNovaSenha;

  public void validar() {
    validarSenhas();
  }

  private void validarSenhas() {
    if (!novaSenha.equals(confirmarNovaSenha)) {
      throw new NegocioException(UsuarioValidationMessages.SENHA_DIFERENTE);
    }

    if (novaSenha.equals(senhaAtual)) {
      throw new NegocioException(UsuarioValidationMessages.SENHA_NOVA_IGUAL_ATUAL);
    }
  }
}
