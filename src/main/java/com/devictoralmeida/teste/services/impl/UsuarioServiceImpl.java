package com.devictoralmeida.teste.services.impl;

import com.devictoralmeida.teste.dto.request.AnexoRequestDto;
import com.devictoralmeida.teste.dto.request.UsuarioRequestDto;
import com.devictoralmeida.teste.entities.*;
import com.devictoralmeida.teste.enums.TipoPerfil;
import com.devictoralmeida.teste.repositories.DadosPessoaFisicaRepository;
import com.devictoralmeida.teste.repositories.DadosPessoaJuridicaRepository;
import com.devictoralmeida.teste.repositories.PessoaPerfilAnexoRepository;
import com.devictoralmeida.teste.repositories.UsuarioRepository;
import com.devictoralmeida.teste.services.FileService;
import com.devictoralmeida.teste.services.UsuarioService;
import com.devictoralmeida.teste.services.rules.RegraPessoaPerfilAnexo;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {
  private UsuarioRepository usuarioRepository;
  private DadosPessoaFisicaRepository dadosPessoaFisicaRepository;
  private DadosPessoaJuridicaRepository dadosPessoaJuridicaRepository;
  private FileService fileService;
  private PessoaPerfilAnexoRepository pessoaPerfilAnexoRepository;

  public UsuarioServiceImpl(UsuarioRepository usuarioRepository, DadosPessoaFisicaRepository dadosPessoaFisicaRepository, DadosPessoaJuridicaRepository dadosPessoaJuridicaRepository, @Qualifier("local") FileService fileService, PessoaPerfilAnexoRepository pessoaPerfilAnexoRepository) {
    this.usuarioRepository = usuarioRepository;
    this.dadosPessoaFisicaRepository = dadosPessoaFisicaRepository;
    this.dadosPessoaJuridicaRepository = dadosPessoaJuridicaRepository;
    this.fileService = fileService;
    this.pessoaPerfilAnexoRepository = pessoaPerfilAnexoRepository;
  }

  @Override
  public void save(UsuarioRequestDto request) {
    request.validar();
    Usuario usuario = new Usuario(request);
    PessoaPerfil pessoaPerfil = createPessoaPerfil(request, usuario);
    usuario.setPessoaPerfil(pessoaPerfil);
    usuarioRepository.save(usuario);
  }

  private PessoaPerfil createPessoaPerfil(UsuarioRequestDto request, Usuario usuario) {
    if (request.getPessoaPerfil().getDadosPessoaFisica() != null) {
      return createPessoaPerfilFisica(request, usuario);
    } else {
      return createPessoaPerfilJuridica(request, usuario);
    }
  }

  private PessoaPerfil createPessoaPerfilFisica(UsuarioRequestDto request, Usuario usuario) {
    DadosPessoaFisica dadosPessoaFisica = new DadosPessoaFisica(request.getPessoaPerfil().getDadosPessoaFisica());
    dadosPessoaFisicaRepository.save(dadosPessoaFisica);
    return new PessoaPerfil(request.getPessoaPerfil(), usuario, dadosPessoaFisica.getId());
  }

  private PessoaPerfil createPessoaPerfilJuridica(UsuarioRequestDto request, Usuario usuario) {
    DadosPessoaJuridica dadosPessoaJuridica = new DadosPessoaJuridica(request.getPessoaPerfil().getDadosPessoaJuridica());
    dadosPessoaJuridicaRepository.save(dadosPessoaJuridica);

    if (isCooperativaAssociacao(usuario)) {
      return createPessoaPerfilComPresidente(request, usuario, dadosPessoaJuridica);
    } else {
      return new PessoaPerfil(request.getPessoaPerfil(), usuario, dadosPessoaJuridica.getId());
    }
  }

  private PessoaPerfil createPessoaPerfilComPresidente(UsuarioRequestDto request, Usuario usuario, DadosPessoaJuridica dadosPessoaJuridica) {
    DadosPessoaFisica dadosPresidente = new DadosPessoaFisica(request.getPessoaPerfil().getPresidente().getDadosPessoais());
    dadosPessoaFisicaRepository.save(dadosPresidente);
    return new PessoaPerfil(request.getPessoaPerfil(), usuario, dadosPessoaJuridica.getId(), dadosPresidente.getId());
  }

  private boolean isCooperativaAssociacao(Usuario usuario) {
    return TipoPerfil.COOPERATIVA.equals(usuario.getTipoPerfil()) || TipoPerfil.ASSOCIACAO.equals(usuario.getTipoPerfil());
  }

  public void uploadAnexos(List<AnexoRequestDto> anexos) {
    Usuario usuario = obterUltimo();
    RegraPessoaPerfilAnexo.validar(anexos, usuario.getTipoPerfil());

    anexos.forEach(anexo -> {
      try {
        fileService.upload(anexo.getArquivo());
        anexo.setNome(anexo.getArquivo().getOriginalFilename() + '.' + anexo.getTipo());
        PessoaPerfilAnexo pessoaPerfilAnexo = new PessoaPerfilAnexo(anexo, usuario);
        usuario.getPessoaPerfil().getAnexos().add(pessoaPerfilAnexo);
        pessoaPerfilAnexoRepository.save(pessoaPerfilAnexo);
      } catch (IOException e) {
        throw new NegocioException("Erro ao fazer upload do arquivo " + anexo.getArquivo().getOriginalFilename() + "Mensagem erro: " + e.getMessage());
      }
    });
  }

  public Usuario obterUltimo() {
    return usuarioRepository.obterUltimo();
  }
}