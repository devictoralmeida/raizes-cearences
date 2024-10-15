package com.devictoralmeida.teste.services.impl;

import com.devictoralmeida.teste.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
  private static Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
  private final JavaMailSender emailSender;

  @Value("${spring.mail.username}")
  private String emailFrom;

  @Override
  public void enviarEmail(String email, String codigoVerificacao) throws MailException {
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setFrom(emailFrom);
      message.setTo(email);
      message.setSubject(email);
      message.setText("Seu código de verificação é: " + codigoVerificacao);
      emailSender.send(message);
      logger.info("Sending email to: " + email);
    } catch (MailException e) {
      throw e;
    }
  }
}

