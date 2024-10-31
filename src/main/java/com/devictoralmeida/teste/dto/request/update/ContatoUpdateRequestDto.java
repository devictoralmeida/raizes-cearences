package com.devictoralmeida.teste.dto.request.update;

import com.devictoralmeida.teste.enums.TipoContato;
import com.devictoralmeida.teste.shared.constants.SharedConstants;
import com.devictoralmeida.teste.shared.constants.validation.ContatoValidationMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class ContatoUpdateRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = -5356988558145183273L;

  private TipoContato preferenciaContato;

  @Pattern(regexp = SharedConstants.REGEX_APENAS_NUMEROS, message = ContatoValidationMessages.WHATSAPP_APENAS_NUMEROS)
  @Size(min = 10, max = SharedConstants.TAMANHO_CONTATO_NUMERICO, message = ContatoValidationMessages.CONTATO_TAMANHO)
  private String numeroWhatsapp;

  @Email(message = ContatoValidationMessages.EMAIL_INVALIDO)
  private String email;
}