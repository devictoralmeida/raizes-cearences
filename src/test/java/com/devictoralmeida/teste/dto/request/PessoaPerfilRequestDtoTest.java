package com.devictoralmeida.teste.dto.request;

import com.devictoralmeida.teste.enums.TipoUsuario;

public class PessoaPerfilRequestDtoTest {
  public static PessoaPerfilRequestDto getPessoaPerfilFisica() {
    PessoaPerfilRequestDto dto = new PessoaPerfilRequestDto();
    dto.setTipoUsuario(TipoUsuario.PESSOA_FISICA);
    dto.setDocumento("57079941067");
    dto.setDadosPessoaFisica(DadosPessoaFisicaRequestDtoTest.getDadosPessoaFisica());
    dto.setVinculo(VinculoRequestDtoTest.getVinculo());
    dto.setContato(ContatoRequestDtoTest.getContatoCompleto());
    dto.setEndereco(EnderecoRequestDtoTest.getEnderecoCompleto());
    return dto;
  }

  public static PessoaPerfilRequestDto getPessoaPerfilJuridica() {
    PessoaPerfilRequestDto dto = new PessoaPerfilRequestDto();
    dto.setTipoUsuario(TipoUsuario.PESSOA_JURIDICA);
    dto.setDocumento("17681552000105");
    dto.setDadosPessoaJuridica(DadosPessoaJuridicaRequestDtoTest.getDadosPessoaJuridicaParcial());
    dto.setVinculo(VinculoRequestDtoTest.getVinculo());
    dto.setContato(ContatoRequestDtoTest.getContatoCompleto());
    dto.setEndereco(EnderecoRequestDtoTest.getEnderecoCompleto());
    return dto;
  }

  public static PessoaPerfilRequestDto getPessoaPerfilCooperativaAssociacao() {
    PessoaPerfilRequestDto dto = getPessoaPerfilJuridica();
    dto.setDadosPessoaJuridica(DadosPessoaJuridicaRequestDtoTest.getDadosPessoaJuridicaCompleto());
    dto.setVinculo(VinculoRequestDtoTest.getVinculo());
    dto.setContato(ContatoRequestDtoTest.getContatoCompleto());
    dto.setEndereco(EnderecoRequestDtoTest.getEnderecoCompleto());
    dto.setPresidente(PresidenteRequestDtoTest.getPresidente());
    return dto;
  }

//  @Test
//  void validar_pessoa_fisica_com_dados_pessoais_duplicados() {
//    PessoaPerfilRequestDto dto = PessoaPerfilRequestDtoTest.getPessoaPerfilFisica();
//    dto.setDadosPessoaJuridica(DadosPessoaJuridicaRequestDtoTest.getDadosPessoaJuridicaCompleto());
//    NegocioException exception = assertThrows(NegocioException.class, () -> dto.validar(TipoPerfil.AGRICULTOR));
//    assertEquals(PessoaPerfilValidationMessages.DADOS_PESSOAIS_EXCLUSIVOS, exception.getMessage());
//  }
//
//  @Test
//  void validar_pessoa_fisica_sem_dados_pessoais() {
//    PessoaPerfilRequestDto dto = new PessoaPerfilRequestDto();
//    dto.setTipoUsuario(TipoUsuario.PESSOA_FISICA);
//    dto.setDocumento("57079941067");
//    NegocioException exception = assertThrows(NegocioException.class, () -> dto.validar(TipoPerfil.AGRICULTOR));
//    assertEquals(PessoaPerfilValidationMessages.DADOS_PESSOAIS_OBRIGATORIOS, exception.getMessage());
//  }
//
//  @Test
//  void validar_cooperativa_associacao_sem_presidente() {
//    PessoaPerfilRequestDto dto = PessoaPerfilRequestDtoTest.getPessoaPerfilCooperativaAssociacao();
//    dto.setPresidente(null);
//    NegocioException exception = assertThrows(NegocioException.class, () -> dto.validar(TipoPerfil.COOPERATIVA));
//    assertEquals(PessoaPerfilValidationMessages.PRESIDENTE_OBRIGATORIO, exception.getMessage());
//  }
//
//  @Test
//  void validar_pessoa_fisica_com_documento_invalido() {
//    PessoaPerfilRequestDto dto = PessoaPerfilRequestDtoTest.getPessoaPerfilFisica();
//    dto.setDocumento("123");
//    NegocioException exception = assertThrows(NegocioException.class, () -> dto.validar(TipoPerfil.AGRICULTOR));
//    assertEquals(PessoaPerfilValidationMessages.DOCUMENTO_CPF_TAMANHO, exception.getMessage());
//  }
//
//  @Test
//  void validar_pessoa_juridica_com_documento_invalido() {
//    PessoaPerfilRequestDto dto = PessoaPerfilRequestDtoTest.getPessoaPerfilJuridica();
//    dto.setDocumento("123");
//    NegocioException exception = assertThrows(NegocioException.class, () -> dto.validar(TipoPerfil.AGROINDUSTRIA));
//    assertEquals(PessoaPerfilValidationMessages.DOCUMENTO_CNPJ_TAMANHO, exception.getMessage());
//  }
//
//  @Test
//  void validar_cooperativa_associacao_com_pessoa_fisica() {
//    PessoaPerfilRequestDto dto = PessoaPerfilRequestDtoTest.getPessoaPerfilFisica();
//    NegocioException exception = assertThrows(NegocioException.class, () -> dto.validar(TipoPerfil.COOPERATIVA));
//    assertEquals(PessoaPerfilValidationMessages.PESSOA_FISICA_NAO_PERMITIDA, exception.getMessage());
//  }

}
