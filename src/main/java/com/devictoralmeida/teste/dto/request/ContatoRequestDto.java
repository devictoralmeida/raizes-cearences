package com.devictoralmeida.teste.dto.request;

import com.devictoralmeida.teste.enums.TipoContato;
import com.devictoralmeida.teste.shared.constants.SharedConstants;
import com.devictoralmeida.teste.shared.constants.validation.ContatoValidationMessages;
import com.devictoralmeida.teste.validations.PresidenteValidationStrategy;
import com.devictoralmeida.teste.validations.impl.ContatoRequestValidationStrategyImpl;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
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

  private PresidenteValidationStrategy<ContatoRequestDto> validationStrategy;

  private TipoContato preferenciaContato;

  @Pattern(regexp = SharedConstants.REGEX_APENAS_NUMEROS, message = ContatoValidationMessages.CONTATO_APENAS_NUMEROS)
  @Size(min = 10, max = SharedConstants.TAMANHO_CONTATO_NUMERICO, message = ContatoValidationMessages.CONTATO_TAMANHO)
  private String numeroContato;

  @Pattern(regexp = SharedConstants.REGEX_APENAS_NUMEROS, message = ContatoValidationMessages.WHATSAPP_APENAS_NUMEROS)
  @Size(min = 10, max = SharedConstants.TAMANHO_CONTATO_NUMERICO, message = ContatoValidationMessages.CONTATO_TAMANHO)
  private String numeroWhatsapp;

  @Email(message = ContatoValidationMessages.EMAIL_INVALIDO)
  private String email;

  @JsonProperty("isWhatsapp")
  private boolean isWhatsapp = false;

  @JsonProperty("isNewsletter")
  private boolean isNewsletter = false;

  public ContatoRequestDto() {
    validationStrategy = new ContatoRequestValidationStrategyImpl();
  }

  public void validar() {
    validationStrategy.validar(this);
  }

  public void validacoesPresidente() {
    validationStrategy.validarPresidente(this);
  }
}