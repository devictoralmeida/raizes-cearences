package com.devictoralmeida.teste.factories;

import com.devictoralmeida.teste.repositories.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Getter
@RequiredArgsConstructor
public class DadosPessoaPerfilTermoRepository {
  private final DadosPessoaFisicaRepository dadosPessoaFisicaRepository;
  private final DadosPessoaJuridicaRepository dadosPessoaJuridicaRepository;
  private final PerfilAcessoRepository perfilAcessoRepository;
  private final TermoCondicaoRepository termoCondicaoRepository;
  private final AnexoRepository anexoRepository;
}
