package com.devictoralmeida.teste.helpers;

import com.devictoralmeida.teste.dto.request.UsuarioRequestDto;
import com.devictoralmeida.teste.shared.utils.FormatarDadosUtils;

public class UsuarioRequestHelper {
  private UsuarioRequestHelper() {
  }

  public static void validar(UsuarioRequestDto dto) {
    PessoaPerfilRequestHelper.validar(dto.getTipoPerfil(), dto.getPessoaPerfil());
    dto.setLogin(dto.getPessoaPerfil().getDocumento());
    FormatarDadosUtils.aplicarTrim(dto);
  }
}
