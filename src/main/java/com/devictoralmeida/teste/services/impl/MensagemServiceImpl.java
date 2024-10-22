package com.devictoralmeida.teste.services.impl;

import com.devictoralmeida.teste.services.MensagemService;
import org.springframework.stereotype.Service;

@Service
public class MensagemServiceImpl implements MensagemService {
  @Override
  public void enviarWhatsapp(String numero, String codigo) {
    System.out.println("Enviando mensagem via WhatsApp para " + numero + " com o código " + codigo);
  }
}
