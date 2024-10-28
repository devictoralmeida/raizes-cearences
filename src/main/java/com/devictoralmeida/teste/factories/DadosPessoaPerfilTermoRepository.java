package com.devictoralmeida.teste.factories;

import com.devictoralmeida.teste.repositories.DadosPessoaFisicaRepository;
import com.devictoralmeida.teste.repositories.DadosPessoaJuridicaRepository;
import com.devictoralmeida.teste.repositories.PessoaPerfilAnexoRepository;
import com.devictoralmeida.teste.repositories.TermoCondicaoRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Getter
@RequiredArgsConstructor
public class DadosPessoaPerfilTermoRepository {
  private final DadosPessoaFisicaRepository dadosPessoaFisicaRepository;
  private final DadosPessoaJuridicaRepository dadosPessoaJuridicaRepository;
  private final PessoaPerfilAnexoRepository pessoaPerfilAnexoRepository;
  private final TermoCondicaoRepository termoCondicaoRepository;
}
