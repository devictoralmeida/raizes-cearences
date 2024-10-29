package com.devictoralmeida.teste.entities;

import com.devictoralmeida.teste.dto.request.ContatoRequestDto;
import com.devictoralmeida.teste.dto.request.ContatoRequestDtoTest;
import com.devictoralmeida.teste.enums.TipoContato;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ContatoTest {
  public static Contato getContato(ContatoRequestDto request) {
    Contato contato = new Contato(request);
    contato.setId(UUID.randomUUID());
    return contato;
  }

  @Test
  void teste_contato_valido() {
    ContatoRequestDto dto = ContatoRequestDtoTest.getContatoCompleto();
    dto.validar();
    Contato contato = getContato(dto);
    assertEquals(TipoContato.WHATSAPP, contato.getPreferenciaContato());
    assertEquals("85999999999", contato.getNumeroContato());
    assertEquals("85999999999", contato.getNumeroWhatsapp());
    assertEquals("teste@email.com", contato.getEmail());
    assertTrue(contato.isWhatsapp());
    assertNotNull(contato.getId());
  }

  @Test
  void teste_contato_presidente_valido() {
    ContatoRequestDto dto = ContatoRequestDtoTest.getContatoCompleto();
    dto.validacoesPresidente();
    Contato contato = getContato(dto);
    assertNull(contato.getPreferenciaContato());
    assertNull(contato.getNumeroWhatsapp());
    assertNotNull(contato.getNumeroContato());
    assertNotNull(contato.getEmail());
    assertFalse(contato.isWhatsapp());
    assertFalse(contato.isNewsletter());
    assertNotNull(contato.getId());
  }
}
