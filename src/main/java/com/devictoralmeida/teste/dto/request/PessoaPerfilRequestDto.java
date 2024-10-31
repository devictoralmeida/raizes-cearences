package com.devictoralmeida.teste.dto.request;

import com.devictoralmeida.teste.enums.TipoUsuario;
import com.devictoralmeida.teste.shared.constants.SharedConstants;
import com.devictoralmeida.teste.shared.constants.validation.PessoaPerfilValidationMessages;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class PessoaPerfilRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 5973184765774917436L;

  @NotNull(message = PessoaPerfilValidationMessages.TIPO_USUARIO_OBRIGATORIO)
  private TipoUsuario tipoUsuario;

  @NotBlank(message = PessoaPerfilValidationMessages.DOCUMENTO_OBRIGATORIO)
  @Pattern(regexp = SharedConstants.REGEX_APENAS_NUMEROS, message = PessoaPerfilValidationMessages.DOCUMENTO_APENAS_NUMEROS)
  @Size(min = SharedConstants.TAMANHO_CPF, max = SharedConstants.TAMANHO_CNPJ, message = PessoaPerfilValidationMessages.DOCUMENTO_TAMANHO)
  private String documento;

  private @Valid DadosPessoaFisicaRequestDto dadosPessoaFisica;
  private @Valid DadosPessoaJuridicaRequestDto dadosPessoaJuridica;
  private @Valid PresidenteRequestDto presidente;

  @NotNull(message = PessoaPerfilValidationMessages.VINCULO_OBRIGATORIO)
  private @Valid VinculoRequestDto vinculo;

  @NotNull(message = PessoaPerfilValidationMessages.CONTATO_OBRIGATORIO)
  private @Valid ContatoRequestDto contato;

  @NotNull(message = PessoaPerfilValidationMessages.ENDERECO_OBRIGATORIO)
  private @Valid EnderecoRequestDto endereco;
}