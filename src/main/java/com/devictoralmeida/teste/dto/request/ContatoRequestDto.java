package com.devictoralmeida.teste.dto.request;

import com.devictoralmeida.teste.dto.request.update.ContatoUpdateRequestDto;
import com.devictoralmeida.teste.shared.constants.SharedConstants;
import com.devictoralmeida.teste.shared.constants.validation.ContatoValidationMessages;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class ContatoRequestDto extends ContatoUpdateRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = -5356988558145183273L;

  @Pattern(regexp = SharedConstants.REGEX_APENAS_NUMEROS, message = ContatoValidationMessages.CONTATO_APENAS_NUMEROS)
  @Size(min = 10, max = SharedConstants.TAMANHO_CONTATO_NUMERICO, message = ContatoValidationMessages.CONTATO_TAMANHO)
  private String numeroContato;

  @JsonProperty("isWhatsapp")
  private boolean isWhatsapp = false;

  @JsonProperty("isNewsletter")
  private boolean isNewsletter = false;
}