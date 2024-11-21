package com.devictoralmeida.teste.helpers;

import com.devictoralmeida.teste.dto.request.PessoaPerfilRequestDto;
import com.devictoralmeida.teste.enums.TipoPerfil;
import com.devictoralmeida.teste.enums.TipoUsuario;
import com.devictoralmeida.teste.shared.constants.SharedConstants;
import com.devictoralmeida.teste.shared.constants.validation.PessoaPerfilValidationMessages;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import com.devictoralmeida.teste.shared.utils.FormatarDadosUtils;

public class PessoaPerfilRequestHelper {
  private PessoaPerfilRequestHelper() {
  }

  public static void validar(TipoPerfil tipoPerfil, PessoaPerfilRequestDto dto) {
    validarExistenciaDuplicidadeDadosPessoais(dto);
    ContatoRequestHelper.validar(dto.getContato());

    boolean isCooperativaAssociacao = TipoPerfil.COOPERATIVA.equals(tipoPerfil) || TipoPerfil.ASSOCIACAO.equals(tipoPerfil);
    validacoesTipoUsuario(isCooperativaAssociacao, dto);

    if (isCooperativaAssociacao) {
      validarCooperativaAssociacao(dto);
    } else if (dto.getPresidente() != null) {
      throw new NegocioException(PessoaPerfilValidationMessages.PRESIDENTE_NAO_PERMITIDO);
    }

    FormatarDadosUtils.aplicarTrim(dto.getEndereco());
    FormatarDadosUtils.aplicarTrim(dto);
  }

  private static void validarCooperativaAssociacao(PessoaPerfilRequestDto dto) {
    validarCampoObrigatorio(dto.getDadosPessoaJuridica(), PessoaPerfilValidationMessages.DADOS_PESSOA_JURIDICA_OBRIGATORIO);
    validarCampoObrigatorio(dto.getPresidente(), PessoaPerfilValidationMessages.PRESIDENTE_OBRIGATORIO);

    DadosPessoaJuridicaRequestHelper.validarCooperativaAssociacao(dto.getDadosPessoaJuridica());
    PresidenteRequestHelper.validar(dto.getPresidente());
  }

  private static void validacoesTipoUsuario(boolean isCooperativaAssociacao, PessoaPerfilRequestDto dto) {
    if (TipoUsuario.PESSOA_FISICA.equals(dto.getTipoUsuario())) {
      validarPessoaFisica(isCooperativaAssociacao, dto);
    } else if (TipoUsuario.PESSOA_JURIDICA.equals(dto.getTipoUsuario())) {
      validarDocumento(SharedConstants.TAMANHO_CNPJ, PessoaPerfilValidationMessages.DOCUMENTO_CNPJ_TAMANHO, dto.getDocumento());
      validarDadosPessoaJuridica(isCooperativaAssociacao, dto);
    }
  }

  private static void validarPessoaFisica(boolean isCooperativaAssociacao, PessoaPerfilRequestDto dto) {
    if (isCooperativaAssociacao) {
      throw new NegocioException(PessoaPerfilValidationMessages.PESSOA_FISICA_NAO_PERMITIDA);
    }
    validarDocumento(SharedConstants.TAMANHO_CPF, PessoaPerfilValidationMessages.DOCUMENTO_CPF_TAMANHO, dto.getDocumento());
    validarDadosPessoaFisica(dto);
  }

  private static void validarExistenciaDuplicidadeDadosPessoais(PessoaPerfilRequestDto dto) {
    if (dto.getDadosPessoaJuridica() == null && dto.getDadosPessoaFisica() == null) {
      throw new NegocioException(PessoaPerfilValidationMessages.DADOS_PESSOAIS_OBRIGATORIOS);
    } else if (dto.getDadosPessoaJuridica() != null && dto.getDadosPessoaFisica() != null) {
      throw new NegocioException(PessoaPerfilValidationMessages.DADOS_PESSOAIS_EXCLUSIVOS);
    }
  }

  private static void validarDocumento(int tamanhoEsperado, String mensagemErro, String documento) {
    if (documento.length() != tamanhoEsperado) {
      throw new NegocioException(mensagemErro);
    }
  }

  private static void validarDadosPessoaJuridica(boolean isCooperativaAssociacao, PessoaPerfilRequestDto dto) {
    validarCampoObrigatorio(dto.getDadosPessoaJuridica(), PessoaPerfilValidationMessages.DADOS_PESSOA_JURIDICA_OBRIGATORIO);
    if (!isCooperativaAssociacao) {
      DadosPessoaJuridicaRequestHelper.validar(dto.getDadosPessoaJuridica());
    }
  }

  private static void validarDadosPessoaFisica(PessoaPerfilRequestDto dto) {
    validarCampoObrigatorio(dto.getDadosPessoaFisica(), PessoaPerfilValidationMessages.DADOS_PESSOA_FISICA_OBRIGATORIO);
    DadosPessoaFisicaRequestHelper.validar(dto.getDadosPessoaFisica());
  }

  private static void validarCampoObrigatorio(Object campo, String mensagemErro) {
    if (campo == null) {
      throw new NegocioException(mensagemErro);
    }
  }
}