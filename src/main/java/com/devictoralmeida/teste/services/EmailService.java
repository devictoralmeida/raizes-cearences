package com.devictoralmeida.teste.services;

public interface EmailService {
  void enviarEmail(String email, String codigoVerificacao, String mensagem);
}
