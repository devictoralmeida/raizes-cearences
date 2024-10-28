package com.devictoralmeida.teste.services.impl;

import com.devictoralmeida.teste.entities.CodigoVerificacao;
import com.devictoralmeida.teste.entities.Usuario;
import com.devictoralmeida.teste.enums.TipoCodigoVerificacao;
import com.devictoralmeida.teste.repositories.CodigoVerificacaoRepository;
import com.devictoralmeida.teste.services.CodigoVerificacaoService;
import com.devictoralmeida.teste.shared.constants.errors.UsuarioErrorsMessageConstants;
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
  public CodigoVerificacao save(TipoCodigoVerificacao tipo, Usuario usuario) {
    if (TipoCodigoVerificacao.CONTATO.equals(tipo)) {
      LocalDateTime dataExpiracao = LocalDateTime.now().plusMinutes(15);
      CodigoVerificacao codigoVerificacao = new CodigoVerificacao(gerarCodigoVerificacaoContato(), dataExpiracao, tipo);
      // Lembrar apagar linha abaixo
      codigoVerificacao.setValido(true);
      return repository.save(codigoVerificacao);
    } else {
      LocalDateTime dataExpiracao = LocalDateTime.now().plusMinutes(15);
      CodigoVerificacao codigoVerificacao = new CodigoVerificacao(gerarCodigoVerificacaoSenha(usuario.getDataCriacao()), dataExpiracao, tipo);
      return repository.save(codigoVerificacao);
    }
  }

  private String gerarCodigoVerificacaoContato() {
    return String.format("%05d", new Random().nextInt(100000));
  }

  private String gerarCodigoVerificacaoSenha(LocalDateTime dataCadastroUsuario) {
    String ano = String.valueOf(dataCadastroUsuario.getYear()).substring(2);
    String mes = String.format("%02d", dataCadastroUsuario.getMonthValue());

    Random random = new Random();
    int primeiroNumero = random.nextInt(9);
    int segundoNumero = (primeiroNumero + 1) % 10;
    String numerosSequenciais = String.format("%d%d", primeiroNumero, segundoNumero);

    return ano + mes + numerosSequenciais;
  }

  @Override
  public void validarCodigoConfirmacao(Usuario usuario, String codigo) {
    LocalDateTime dataAtual = LocalDateTime.now();
    LocalDateTime dataExpiracao = usuario.getCodigoVerificacao().getDataExpiracao();
    boolean codigoInvalido = !usuario.getCodigoVerificacao().getCodigo().equals(codigo);
    boolean codigoExpirado = !dataAtual.isBefore(dataExpiracao);

    if (codigoInvalido || codigoExpirado) {
      throw new NegocioException(UsuarioErrorsMessageConstants.CODIGO_INVALIDO_EXPIRADO);
    }

    usuario.getCodigoVerificacao().setValido(true);
  }
}
