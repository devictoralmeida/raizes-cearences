package com.devictoralmeida.teste.services.rules;

import com.devictoralmeida.teste.dto.request.AnexoRequestDto;
import com.devictoralmeida.teste.enums.TipoDocumento;
import com.devictoralmeida.teste.enums.TipoPerfil;
import com.devictoralmeida.teste.shared.constants.validation.AnexoValidationMessages;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RegraPessoaPerfilAnexo {
  private static final Map<TipoPerfil, Set<TipoDocumento>> PERFIL_DOCUMENTOS_OBRIGATORIOS = Map.of(
          TipoPerfil.COOPERATIVA, Set.of(
                  TipoDocumento.ATA_ASSEMBLEIA_GERAL,
                  TipoDocumento.ATA_FUNDACAO,
                  TipoDocumento.ATA_ULTIMA_ELEICAO,
                  TipoDocumento.ESTATUTO
          ),
          TipoPerfil.ASSOCIACAO, Set.of(
                  TipoDocumento.ATA_FUNDACAO,
                  TipoDocumento.ATA_ULTIMA_ELEICAO,
                  TipoDocumento.ESTATUTO
          )
  );

  private RegraPessoaPerfilAnexo() {
  }

  public static void validar(List<AnexoRequestDto> anexos, TipoPerfil tipoPerfil) {
    anexos.forEach(AnexoRequestDto::validar);

    if (!PERFIL_DOCUMENTOS_OBRIGATORIOS.containsKey(tipoPerfil)) {
      throw new NegocioException(AnexoValidationMessages.TIPO_PERFIL_INVALIDO);
    }

    if (tipoPerfil == TipoPerfil.ASSOCIACAO) {
      validarAnexosAssociacao(anexos);
    }

    validarDocumentosObrigatorios(anexos, PERFIL_DOCUMENTOS_OBRIGATORIOS.get(tipoPerfil));
  }

  private static void validarAnexosAssociacao(List<AnexoRequestDto> anexos) {
    List<TipoDocumento> tiposDocumentoEnviados = anexos.stream().map(AnexoRequestDto::getTipoDocumento).toList();

    if (tiposDocumentoEnviados.contains(TipoDocumento.ATA_ASSEMBLEIA_GERAL)) {
      throw new NegocioException(AnexoValidationMessages.ATA_ASSEMBLEIA_GERAL_NAO_PERMITIDA);
    }
  }

  private static void validarDocumentosObrigatorios(List<AnexoRequestDto> anexos, Set<TipoDocumento> documentosObrigatorios) {
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