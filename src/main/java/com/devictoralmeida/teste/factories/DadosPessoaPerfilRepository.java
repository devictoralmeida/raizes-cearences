package com.devictoralmeida.teste.factories;

import com.devictoralmeida.teste.repositories.DadosPessoaFisicaRepository;
import com.devictoralmeida.teste.repositories.DadosPessoaJuridicaRepository;
import com.devictoralmeida.teste.repositories.PessoaPerfilAnexoRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Getter
@RequiredArgsConstructor
public class DadosPessoaPerfilRepository {
  private final DadosPessoaFisicaRepository dadosPessoaFisicaRepository;
  private final DadosPessoaJuridicaRepository dadosPessoaJuridicaRepository;
  private final PessoaPerfilAnexoRepository pessoaPerfilAnexoRepository;
}
