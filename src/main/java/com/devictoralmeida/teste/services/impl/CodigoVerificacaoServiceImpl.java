package com.devictoralmeida.teste.services.impl;

import com.devictoralmeida.teste.entities.CodigoVerificacao;
import com.devictoralmeida.teste.entities.Usuario;
import com.devictoralmeida.teste.repositories.CodigoVerificacaoRepository;
import com.devictoralmeida.teste.services.CodigoVerificacaoService;
import com.devictoralmeida.teste.shared.constants.validation.UsuarioValidationMessages;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CodigoVerificacaoServiceImpl implements CodigoVerificacaoService {
  private final CodigoVerificacaoRepository repository;

  @Transactional
  @Override
  public CodigoVerificacao save(String codigo) {
    LocalDateTime dataExpiracao = LocalDateTime.now().plusMinutes(15);
    CodigoVerificacao codigoVerificacao = new CodigoVerificacao(codigo, dataExpiracao);
    return repository.save(codigoVerificacao);
  }

  @Override
  public String generateVerificationCode() {
    return String.format("%05d", new Random().nextInt(100000));
  }

  @Override
  public void validarCodigoConfirmacao(Usuario usuario, String codigo) {
    if (usuario.getCodigoVerificacao() != null) {
      LocalDateTime dataAtual = LocalDateTime.now();
      LocalDateTime dataExpiracao = usuario.getCodigoVerificacao().getDataExpiracao();

      if (!dataAtual.isBefore(dataExpiracao)) {
        throw new NegocioException(UsuarioValidationMessages.CODIGO_EXPIRADO);
      }

      if (!usuario.getCodigoVerificacao().getCodigo().equals(codigo)) {
        throw new NegocioException(UsuarioValidationMessages.CODIGO_INVALIDO);
      }
    } else {
      throw new NegocioException(UsuarioValidationMessages.CODIGO_NAO_INFORMADO);
    }

    usuario.getCodigoVerificacao().setValido(true);
  }
}
