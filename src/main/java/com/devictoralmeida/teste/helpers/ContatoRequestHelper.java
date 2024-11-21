package com.devictoralmeida.teste.helpers;

import com.devictoralmeida.teste.dto.request.ContatoRequestDto;
import com.devictoralmeida.teste.dto.request.update.ContatoUpdateRequestDto;
import com.devictoralmeida.teste.enums.TipoContato;
import com.devictoralmeida.teste.shared.constants.SharedConstants;
import com.devictoralmeida.teste.shared.constants.validation.ContatoValidationMessages;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import com.devictoralmeida.teste.shared.utils.FormatarDadosUtils;

public class ContatoRequestHelper {

  private ContatoRequestHelper() {
  }

  public static void validar(ContatoRequestDto dto) {
    validaWhatsApp(dto);
    validaExistenciaContato(dto);
    validaPreferenciaContato(dto);
    validaTamanhoEmail(dto);
    FormatarDadosUtils.aplicarTrim(dto);
  }

  public static void validar(ContatoUpdateRequestDto dto) {
    validaExistenciaPreferenciaContato(dto);
    validaExistenciaContato(dto);
    validaPreferenciaContato(dto);
    validaTamanhoEmail(dto);
    FormatarDadosUtils.aplicarTrim(dto);
  }

  public static void validacoesPresidente(ContatoRequestDto dto) {
    validarCampoObrigatorio(dto.getEmail(), ContatoValidationMessages.EMAIL_OBRIGATORIO);
    validarCampoObrigatorio(dto.getNumeroContato(), ContatoValidationMessages.NUMERO_CONTATO_OBRIGATORIO);
    validaTamanhoEmail(dto);
    resetarCamposNaoPermitidosPresidente(dto);
    FormatarDadosUtils.aplicarTrim(dto);
  }

  private static void validaExistenciaPreferenciaContato(ContatoUpdateRequestDto dto) {
    if (dto.getPreferenciaContato() == null) {
      throw new NegocioException(ContatoValidationMessages.PREFERENCIA_CONTATO_OBRIGATORIA);
    }
  }

  private static void resetarCamposNaoPermitidosPresidente(ContatoRequestDto dto) {
    dto.setPreferenciaContato(null);
    dto.setNumeroWhatsapp(null);
    dto.setWhatsapp(false);
    dto.setNewsletter(false);
  }

  private static void validaWhatsApp(ContatoRequestDto dto) {
    if (dto.isWhatsapp()) {
      if (dto.getNumeroContato() == null) {
        throw new NegocioException(ContatoValidationMessages.NUMERO_CONTATO_OBRIGATORIO);
      }

      dto.setNumeroWhatsapp(dto.getNumeroContato());
    }
  }

  private static void validaExistenciaContato(ContatoRequestDto dto) {
    if (dto.getNumeroContato() == null && dto.getNumeroWhatsapp() == null && dto.getEmail() == null) {
      throw new NegocioException(ContatoValidationMessages.CONTATO_OBRIGATORIO);
    }
  }

  private static void validaExistenciaContato(ContatoUpdateRequestDto dto) {
    if (dto.getNumeroWhatsapp() == null && dto.getEmail() == null) {
      throw new NegocioException(ContatoValidationMessages.CONTATO_OBRIGATORIO);
    } else if (dto.getNumeroWhatsapp() != null && dto.getEmail() != null) {
      throw new NegocioException(ContatoValidationMessages.CONTATO_DIVERGENTE);
    }
  }

  private static void validaPreferenciaContato(ContatoUpdateRequestDto dto) {
    if (dto.getPreferenciaContato() == null) {
      throw new NegocioException(ContatoValidationMessages.PREFERENCIA_CONTATO_OBRIGATORIA);
    } else {
      if (TipoContato.EMAIL.equals(dto.getPreferenciaContato()) && dto.getEmail() == null) {
        throw new NegocioException(ContatoValidationMessages.EMAIL_OBRIGATORIO);
      }

      if (TipoContato.WHATSAPP.equals(dto.getPreferenciaContato()) && dto.getNumeroWhatsapp() == null) {
        throw new NegocioException(ContatoValidationMessages.CONTATO_PREFERENCIA_CONTATO_DIVERGENTE);
      }
    }
  }

  private static void validarCampoObrigatorio(String campo, String mensagemErro) {
    if (campo == null) {
      throw new NegocioException(mensagemErro);
    }
  }

  private static void validaTamanhoEmail(ContatoUpdateRequestDto dto) {
    if (dto.getEmail() != null) {
      String[] partesEmail = dto.getEmail().split("@");
      String parteLocal = partesEmail[0];
      String parteDominio = partesEmail[1];

      if (parteLocal.length() > SharedConstants.TAMANHO_MAXIMO_LOCAL_EMAIL || parteDominio.length() > SharedConstants.TAMANHO_MAXIMO_DOMINIO_EMAIL) {
        throw new NegocioException(ContatoValidationMessages.EMAIL_TAMANHO);
      }
    }
  }
}
