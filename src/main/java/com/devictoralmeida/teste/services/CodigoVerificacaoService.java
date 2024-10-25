package com.devictoralmeida.teste.services;

import com.devictoralmeida.teste.entities.CodigoVerificacao;
import com.devictoralmeida.teste.entities.Usuario;
import com.devictoralmeida.teste.enums.TipoCodigoVerificacao;

import java.time.LocalDateTime;

public interface CodigoVerificacaoService {
  CodigoVerificacao save(TipoCodigoVerificacao tipo);

  String gerarCodigoVerificacaoContato();

  String gerarCodigoVerificacaoSenha(LocalDateTime dataCadastroUsuario);

  void validarCodigoConfirmacao(Usuario usuario, String codigo);
}
