package com.devictoralmeida.teste.dto.request;

import com.devictoralmeida.teste.enums.TipoPerfil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsuarioRequestDtoTest {
  public static UsuarioRequestDto getUsuarioAgricultorPessoaFisica() {
    UsuarioRequestDto dto = new UsuarioRequestDto();
    dto.setTipoPerfil(TipoPerfil.AGRICULTOR);
    dto.setPessoaPerfil(PessoaPerfilRequestDtoTest.getPessoaPerfilFisica());
    dto.setLogin(dto.getPessoaPerfil().getDocumento());
    return dto;
  }

  public static UsuarioRequestDto getUsuarioAgroindustriaPessoaJuridica() {
    UsuarioRequestDto dto = new UsuarioRequestDto();
    dto.setTipoPerfil(TipoPerfil.AGROINDUSTRIA);
    dto.setPessoaPerfil(PessoaPerfilRequestDtoTest.getPessoaPerfilJuridica());
    dto.setLogin(dto.getPessoaPerfil().getDocumento());
    return dto;
  }

  public static UsuarioRequestDto getUsuarioCooperativa() {
    UsuarioRequestDto dto = new UsuarioRequestDto();
    dto.setTipoPerfil(TipoPerfil.COOPERATIVA);
    dto.setPessoaPerfil(PessoaPerfilRequestDtoTest.getPessoaPerfilCooperativaAssociacao());
    dto.setLogin(dto.getPessoaPerfil().getDocumento());
    return dto;
  }

  public static UsuarioRequestDto getUsuarioAssociacao() {
    UsuarioRequestDto dto = getUsuarioCooperativa();
    dto.setTipoPerfil(TipoPerfil.ASSOCIACAO);
    return dto;
  }

  @Test
  void test_setter_login_ok() {
    UsuarioRequestDto dto = getUsuarioAgricultorPessoaFisica();
    assertEquals(PessoaPerfilRequestDtoTest.getPessoaPerfilFisica().getDocumento(), dto.getLogin());
  }
}
