package com.devictoralmeida.teste.dto.request;


import com.devictoralmeida.teste.shared.constants.SharedConstants;
import com.devictoralmeida.teste.shared.constants.validation.EnderecoValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class EnderecoRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 432685855766819626L;

  @NotBlank(message = EnderecoValidationMessages.CEP_OBRIGATORIO)
  @Pattern(regexp = SharedConstants.REGEX_APENAS_NUMEROS, message = EnderecoValidationMessages.CEP_APENAS_NUMEROS)
  @Size(min = 8, max = 8, message = EnderecoValidationMessages.CEP_TAMANHO)
  private String cep;

  @NotBlank(message = EnderecoValidationMessages.MUNICIPIO_OBRIGATORIO)
  @Size(max = 100, message = EnderecoValidationMessages.MUNICIPIO_TAMANHO)
  private String municipio;

  //  @NotBlank(message = EnderecoValidationMessages.LOCALIDADE_OBRIGATORIA)
  @Size(max = 100, message = EnderecoValidationMessages.LOCALIDADE_TAMANHO)
  private String localidade;

  //  @NotBlank(message = EnderecoValidationMessages.LOGRADOURO_OBRIGATORIO)
  @Size(max = 100, message = EnderecoValidationMessages.LOGRADOURO_TAMANHO)
  private String logradouro;

  //  @NotBlank(message = EnderecoValidationMessages.NUMERO_OBRIGATORIO)
  @Pattern(regexp = SharedConstants.REGEX_ALFANUMERICO, message = EnderecoValidationMessages.NUMERO_ALFANUMERICO)
  @Size(max = 10, message = EnderecoValidationMessages.NUMERO_TAMANHO)
  private String numero;

  @Size(max = 50, message = EnderecoValidationMessages.COMPLEMENTO_TAMANHO)
  private String complemento;

  @Size(max = 50, message = EnderecoValidationMessages.BAIRRO_TAMANHO)
  private String bairro;

  @NotBlank(message = EnderecoValidationMessages.UF_OBRIGATORIA)
  @Size(min = 2, max = 2, message = EnderecoValidationMessages.UF_TAMANHO)
  private String uf;

  @Size(max = 50, message = EnderecoValidationMessages.PONTO_REFERENCIA_TAMANHO)
  private String pontoReferencia;
}
