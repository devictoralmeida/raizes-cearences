package com.devictoralmeida.teste.services;

import com.devictoralmeida.teste.entities.CodigoVerificacao;
import com.devictoralmeida.teste.entities.Usuario;
import com.devictoralmeida.teste.enums.TipoCodigoVerificacao;

public interface CodigoVerificacaoService {
  CodigoVerificacao save(TipoCodigoVerificacao tipo, Usuario usuario);

  void validarCodigoConfirmacao(Usuario usuario, String codigo);
}
