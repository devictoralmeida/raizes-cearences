package com.devictoralmeida.teste.dto.request;

import com.devictoralmeida.teste.enums.TipoContato;
import com.devictoralmeida.teste.shared.constants.SharedConstants;
import com.devictoralmeida.teste.shared.constants.validation.ContatoValidationMessages;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
public class ContatoUpdateRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = -5356988558145183273L;

  @NotNull(message = ContatoValidationMessages.PREFERENCIA_CONTATO_OBRIGATORIA)
  private TipoContato preferenciaContato;

  @Pattern(regexp = SharedConstants.REGEX_APENAS_NUMEROS, message = ContatoValidationMessages.WHATSAPP_APENAS_NUMEROS)
  @Size(min = 10, max = SharedConstants.TAMANHO_CONTATO_NUMERICO, message = ContatoValidationMessages.CONTATO_TAMANHO)
  private String numeroWhatsapp;

  @Email(message = ContatoValidationMessages.EMAIL_INVALIDO)
  @Size(max = 320, message = ContatoValidationMessages.EMAIL_TAMANHO)
  private String email;

  public void validar() {
    validaExistenciaContato();
    validaPreferenciaContato();
  }

  private void validaExistenciaContato() {
    if (numeroWhatsapp == null && email == null) {
      throw new NegocioException(ContatoValidationMessages.CONTATO_OBRIGATORIO);
    } else if (numeroWhatsapp != null && email != null) {
      throw new NegocioException(ContatoValidationMessages.CONTATO_DIVERGENTE);
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