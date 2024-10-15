package com.devictoralmeida.teste.dto.request;

import com.devictoralmeida.teste.enums.TipoPerfil;
import com.devictoralmeida.teste.shared.constants.SharedConstants;
import com.devictoralmeida.teste.shared.constants.validation.UsuarioValidationMessages;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class UsuarioRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = -5140952734522230653L;
  private String login;

  @NotNull(message = UsuarioValidationMessages.TIPO_PERFIL_OBRIGATORIO)
  private TipoPerfil tipoPerfil;

  private String firebaseUID;

  @NotBlank(message = UsuarioValidationMessages.SENHA_OBRIGATORIA)
  @Size(min = 8, max = 32, message = UsuarioValidationMessages.SENHA_TAMANHO)
  @Pattern(regexp = SharedConstants.REGEX_VALIDA_SENHA, message = UsuarioValidationMessages.SENHA_INVALIDA)
  private String senha;

  @NotBlank(message = UsuarioValidationMessages.CONFIRMACAO_SENHA_OBRIGATORIA)
  private String confirmacaoSenha;

  @NotNull(message = UsuarioValidationMessages.PESSOA_PERFIL_OBRIGATORIA)
  private @Valid PessoaPerfilRequestDto pessoaPerfil;

  public void validar() {
    pessoaPerfil.validar(tipoPerfil);
    validarSenhas();
    login = pessoaPerfil.getDocumento();
  }

  private void validarSenhas() {
    if (!senha.equals(confirmacaoSenha)) {
      throw new NegocioException(UsuarioValidationMessages.SENHA_DIFERENTE);
    }
  }
}
