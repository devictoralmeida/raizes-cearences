package com.devictoralmeida.teste.dto.request;

import com.devictoralmeida.teste.enums.TipoPerfil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UsuarioRequestDto implements Serializable {
  private String login;
  private TipoPerfil tipoPerfil;
  private String firebaseUID;

  @NotNull(message = "O perfil da pessoa é obrigatório")
  private @Valid PessoaPerfilRequestDto pessoaPerfil;

  public void validar() {
    pessoaPerfil.validar(tipoPerfil);
  }
}
