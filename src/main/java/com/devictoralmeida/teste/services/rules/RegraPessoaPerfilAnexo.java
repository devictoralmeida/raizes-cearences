package com.devictoralmeida.teste.services.rules;

import com.devictoralmeida.teste.dto.request.AnexoRequestDto;
import com.devictoralmeida.teste.enums.TipoDocumento;
import com.devictoralmeida.teste.enums.TipoPerfil;
import com.devictoralmeida.teste.shared.constants.validation.AnexoValidationMessages;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;

import java.util.List;

public class RegraPessoaPerfilAnexo {
  private RegraPessoaPerfilAnexo() {
  }

  public static void validar(List<AnexoRequestDto> anexos, TipoPerfil tipoPerfil) {
    anexos.forEach(AnexoRequestDto::validar);

    switch (tipoPerfil) {
      case AGRICULTOR, CONSUMIDOR -> throw new NegocioException(AnexoValidationMessages.TIPO_PERFIL_INVALIDO);
      case AGROINDUSTRIA, COOPERATIVA -> validarAnexosAgroindustriaCooperativa(anexos);
      case ASSOCIACAO -> validarAnexosAssociacao(anexos);
    }
  }

  public static void validarAnexosAgroindustriaCooperativa(List<AnexoRequestDto> anexos) {
    validarDocumentosObrigatorios(anexos, List.of(
            TipoDocumento.ATA_ASSEMBLEIA_GERAL,
            TipoDocumento.ATA_FUNDACAO,
            TipoDocumento.ATA_ULTIMA_ELEICAO,
            TipoDocumento.ESTATUTO
    ));
  }

  public static void validarAnexosAssociacao(List<AnexoRequestDto> anexos) {
    validarDocumentosObrigatorios(anexos, List.of(
            TipoDocumento.ATA_FUNDACAO,
            TipoDocumento.ATA_ULTIMA_ELEICAO,
            TipoDocumento.ESTATUTO
    ));
  }

  private static void validarDocumentosObrigatorios(List<AnexoRequestDto> anexos, List<TipoDocumento> documentosObrigatorios) {
    List<TipoDocumento> tiposDocumentoEnviados = anexos.stream().map(AnexoRequestDto::getTipoDocumento).toList();

    for (TipoDocumento documento : documentosObrigatorios) {
      if (!tiposDocumentoEnviados.contains(documento)) {
        throw new NegocioException(getMensagemErro(documento));
      }
    }
  }

  private static String getMensagemErro(TipoDocumento documento) {
    return switch (documento) {
      case ATA_ASSEMBLEIA_GERAL -> AnexoValidationMessages.ATA_ASSEMBLEIA_GERAL_OBRIGATORIA;
      case ATA_FUNDACAO -> AnexoValidationMessages.ATA_FUNDACAO_OBRIGATORIA;
      case ATA_ULTIMA_ELEICAO -> AnexoValidationMessages.ATA_ULTIMA_ELEICAO_OBRIGATORIA;
      case ESTATUTO -> AnexoValidationMessages.ESTATUTO_OBRIGATORIO;
    };
  }
}
