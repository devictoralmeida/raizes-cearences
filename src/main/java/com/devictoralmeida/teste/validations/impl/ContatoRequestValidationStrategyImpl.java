package com.devictoralmeida.teste.validations.impl;

import com.devictoralmeida.teste.dto.request.ContatoRequestDto;
import com.devictoralmeida.teste.enums.TipoContato;
import com.devictoralmeida.teste.shared.constants.SharedConstants;
import com.devictoralmeida.teste.shared.constants.validation.ContatoValidationMessages;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import com.devictoralmeida.teste.validations.PresidenteValidationStrategy;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class ContatoRequestValidationStrategyImpl implements PresidenteValidationStrategy<ContatoRequestDto>, Serializable {
  @Serial
  private static final long serialVersionUID = 247251871339792039L;

  private ContatoRequestDto dto;

  @Override
  public void validar(ContatoRequestDto dto) {
    this.dto = dto;
    validaWhatsApp();
    validaExistenciaContato();
    validaPreferenciaContato();
    validaTamanhoEmail();
  }

  @Override
  public void validarPresidente(ContatoRequestDto dto) {
    this.dto = dto;
    validarCampoObrigatorio(dto.getEmail(), ContatoValidationMessages.EMAIL_OBRIGATORIO);
    validarCampoObrigatorio(dto.getNumeroContato(), ContatoValidationMessages.NUMERO_CONTATO_OBRIGATORIO);
    validaTamanhoEmail();
    resetarCamposNaoPermitidosPresidente();
  }

  private void resetarCamposNaoPermitidosPresidente() {
    dto.setPreferenciaContato(null);
    dto.setNumeroWhatsapp(null);
    dto.setWhatsapp(false);
    dto.setNewsletter(false);
  }

  private void validaWhatsApp() {
    if (TipoContato.WHATSAPP.equals(dto.getPreferenciaContato())) {
      dto.setWhatsapp(true);
    }

    if (dto.isWhatsapp() && dto.getNumeroContato() != null) {
      dto.setNumeroWhatsapp(dto.getNumeroContato());
    }

    if (dto.isWhatsapp() && dto.getNumeroContato() == null && dto.getNumeroWhatsapp() == null) {
      throw new NegocioException(ContatoValidationMessages.CONTATO_WHATSAPP_OBRIGATORIO);
    }
  }

  private void validaExistenciaContato() {
    if (dto.getNumeroContato() == null && dto.getNumeroWhatsapp() == null && dto.getEmail() == null) {
      throw new NegocioException(ContatoValidationMessages.CONTATO_OBRIGATORIO);
    }
  }

  private void validaPreferenciaContato() {
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

  private void validarCampoObrigatorio(String campo, String mensagemErro) {
    if (campo == null) {
      throw new NegocioException(mensagemErro);
    }
  }

  private void validaTamanhoEmail() {
    if (Objects.nonNull(dto.getEmail())) {
      String[] partesEmail = dto.getEmail().split("@");
      String parteLocal = partesEmail[0];
      String parteDominio = partesEmail[1];

      if (parteLocal.length() > SharedConstants.TAMANHO_MAXIMO_LOCAL_EMAIL || parteDominio.length() > SharedConstants.TAMANHO_MAXIMO_DOMINIO_EMAIL) {
        throw new NegocioException(ContatoValidationMessages.EMAIL_TAMANHO);
      }
    }
  }
}
