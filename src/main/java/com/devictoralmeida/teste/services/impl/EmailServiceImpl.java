package com.devictoralmeida.teste.services.impl;

import com.devictoralmeida.teste.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
  private final JavaMailSender emailSender;

  @Value("${spring.mail.username}")
  private String emailFrom;

  @Override
  public void enviarEmail(String email, String codigoVerificacao, String mensagem) {
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setFrom(emailFrom);
      message.setTo(email);
      message.setSubject(email);
      message.setText(mensagem + codigoVerificacao);
      emailSender.send(message);
    } catch (MailException e) {
      throw new RuntimeException("Erro ao enviar e-mail");
    }
  }
}

