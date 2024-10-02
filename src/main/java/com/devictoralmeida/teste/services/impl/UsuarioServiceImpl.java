package com.devictoralmeida.teste.services.impl;

import com.devictoralmeida.teste.dto.request.UsuarioRequestDto;
import com.devictoralmeida.teste.entities.DadosPessoaFisica;
import com.devictoralmeida.teste.entities.DadosPessoaJuridica;
import com.devictoralmeida.teste.entities.PessoaPerfil;
import com.devictoralmeida.teste.entities.Usuario;
import com.devictoralmeida.teste.repositories.DadosPessoaFisicaRepository;
import com.devictoralmeida.teste.repositories.DadosPessoaJuridicaRepository;
import com.devictoralmeida.teste.repositories.UsuarioRepository;
import com.devictoralmeida.teste.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
  private final UsuarioRepository usuarioRepository;
  private final DadosPessoaFisicaRepository dadosPessoaFisicaRepository;
  private final DadosPessoaJuridicaRepository dadosPessoaJuridicaRepository;

  @Override
  public void save(UsuarioRequestDto request) {
    request.validar();
    Usuario usuario = new Usuario(request);
    PessoaPerfil pessoaPerfil = createPessoaPerfil(request, usuario);
    usuario.setPessoaPerfil(pessoaPerfil);
    usuarioRepository.save(usuario);
  }

  private PessoaPerfil createPessoaPerfil(UsuarioRequestDto request, Usuario usuario) {
    PessoaPerfil pessoaPerfil;

    if (request.getPessoaPerfil().getDadosPessoaFisica() != null) {
      DadosPessoaFisica dadosPessoaFisica = new DadosPessoaFisica(request.getPessoaPerfil().getDadosPessoaFisica());
      dadosPessoaFisicaRepository.save(dadosPessoaFisica);
      pessoaPerfil = new PessoaPerfil(request.getPessoaPerfil(), usuario, dadosPessoaFisica);
    } else {
      DadosPessoaJuridica dadosPessoaJuridica = new DadosPessoaJuridica(request.getPessoaPerfil().getDadosPessoaJuridica());
      dadosPessoaJuridicaRepository.save(dadosPessoaJuridica);
      pessoaPerfil = new PessoaPerfil(request.getPessoaPerfil(), usuario, dadosPessoaJuridica);
    }

    return pessoaPerfil;
  }
}