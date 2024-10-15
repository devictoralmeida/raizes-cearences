package com.devictoralmeida.teste.services;

import com.devictoralmeida.teste.entities.CodigoVerificacao;
import com.devictoralmeida.teste.entities.Usuario;

public interface CodigoVerificacaoService {
  CodigoVerificacao save(String codigo);

  String generateVerificationCode();

  void validarCodigoConfirmacao(Usuario usuario, String codigo);
}
