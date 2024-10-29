package com.devictoralmeida.teste.dto.request;

import com.devictoralmeida.teste.enums.TipoContato;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContatoRequestDtoTest {
  public static ContatoRequestDto getContatoCompleto() {
    ContatoRequestDto dto = new ContatoRequestDto();
    dto.setNumeroContato("85999999999");
    dto.setPreferenciaContato(TipoContato.WHATSAPP);
    dto.setEmail("teste@email.com");
    return dto;
  }

  public static ContatoRequestDto getContatoWhatsapp() {
    ContatoRequestDto dto = new ContatoRequestDto();
    dto.setNumeroContato("85999999999");
    dto.setPreferenciaContato(TipoContato.WHATSAPP);
    dto.setNewsletter(true);
    return dto;
  }

  public static ContatoRequestDto getContatoEmail() {
    ContatoRequestDto dto = new ContatoRequestDto();
    dto.setPreferenciaContato(TipoContato.EMAIL);
    dto.setEmail("teste@email.com");
    dto.setNewsletter(true);
    return dto;
  }

  public static ContatoRequestDto getContatoPresidente() {
    ContatoRequestDto dto = new ContatoRequestDto();
    dto.setNumeroContato("85999999998");
    dto.setEmail("teste2@email.com");
    return dto;
  }

  @Test
  void teste_contato_valido() {
    ContatoRequestDto dto = ContatoRequestDtoTest.getContatoCompleto();
    dto.validar();
    assertEquals(TipoContato.WHATSAPP, dto.getPreferenciaContato());
    assertEquals("85999999999", dto.getNumeroContato());
    assertEquals("85999999999", dto.getNumeroWhatsapp());
    assertEquals("teste@email.com", dto.getEmail());
    assertTrue(dto.isWhatsapp());
  }

  @Test
  void teste_contato_presidente_valido() {
    ContatoRequestDto dto = ContatoRequestDtoTest.getContatoCompleto();
    dto.validacoesPresidente();
    assertNull(dto.getPreferenciaContato());
    assertNull(dto.getNumeroWhatsapp());
    assertNotNull(dto.getNumeroContato());
    assertNotNull(dto.getEmail());
    assertFalse(dto.isWhatsapp());
    assertFalse(dto.isNewsletter());
  }
}
