package com.devictoralmeida.teste.dto.request;

import com.devictoralmeida.teste.enums.TipoContato;
import com.devictoralmeida.teste.shared.constants.SharedConstants;
import com.devictoralmeida.teste.shared.constants.validation.ContatoValidationMessages;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class ContatoRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = -5356988558145183273L;

  @NotNull(message = ContatoValidationMessages.PREFERENCIA_CONTATO_OBRIGATORIA)
  private TipoContato preferenciaContato;

  @Pattern(regexp = SharedConstants.REGEX_APENAS_NUMEROS, message = ContatoValidationMessages.CONTATO_APENAS_NUMEROS)
  @Size(min = SharedConstants.TAMANHO_CONTATO_NUMERICO, max = SharedConstants.TAMANHO_CONTATO_NUMERICO, message = ContatoValidationMessages.CONTATO_TAMANHO)
  private String numeroContato;

  @Pattern(regexp = SharedConstants.REGEX_APENAS_NUMEROS, message = ContatoValidationMessages.WHATSAPP_APENAS_NUMEROS)
  @Size(min = SharedConstants.TAMANHO_CONTATO_NUMERICO, max = SharedConstants.TAMANHO_CONTATO_NUMERICO, message = ContatoValidationMessages.CONTATO_TAMANHO)
  private String numeroWhatsapp;

  @Email(message = ContatoValidationMessages.EMAIL_INVALIDO)
  @Size(max = 320, message = ContatoValidationMessages.EMAIL_TAMANHO)
  private String email;

  @JsonProperty("isWhatsapp")
  private boolean isWhatsapp = false;

  @JsonProperty("isNewsletter")
  private boolean isNewsletter = false;

  public void validar() {
    validaWhatsApp();
    validaExistenciaContato();
    validaPreferenciaContato();
  }

  private void validaWhatsApp() {
    if (isWhatsapp && numeroContato != null) {
      numeroWhatsapp = numeroContato;
    }

    if (isWhatsapp && numeroContato == null && numeroWhatsapp == null) {
      throw new NegocioException(ContatoValidationMessages.CONTATO_WHATSAPP_OBRIGATORIO);
    }
  }

  private void validaExistenciaContato() {
    if (numeroContato == null && numeroWhatsapp == null && email == null) {
      throw new NegocioException(ContatoValidationMessages.CONTATO_OBRIGATORIO);
    }
  }

  private void validaPreferenciaContato() {
    if (TipoContato.EMAIL.equals(preferenciaContato) && email == null) {
      throw new NegocioException(ContatoValidationMessages.EMAIL_OBRIGATORIO);
    }

    if (TipoContato.WHATSAPP.equals(preferenciaContato) && numeroWhatsapp == null) {
      throw new NegocioException(ContatoValidationMessages.CONTATO_PREFERENCIA_CONTATO_DIVERGENTE);
    }
  }
}
