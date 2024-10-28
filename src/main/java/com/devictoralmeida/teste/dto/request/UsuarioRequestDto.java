package com.devictoralmeida.teste.dto.request;

import com.devictoralmeida.teste.enums.TipoPerfil;
import com.devictoralmeida.teste.shared.constants.validation.UsuarioValidationMessages;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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

  @NotNull(message = UsuarioValidationMessages.PESSOA_PERFIL_OBRIGATORIA)
  private @Valid PessoaPerfilRequestDto pessoaPerfil;

  public void validar() {
    pessoaPerfil.validar(tipoPerfil);
    login = pessoaPerfil.getDocumento();
  }
}
