package com.devictoralmeida.teste.dto.request;

import com.devictoralmeida.teste.enums.TipoPerfil;
import com.devictoralmeida.teste.enums.TipoUsuario;
import com.devictoralmeida.teste.shared.constants.SharedConstants;
import com.devictoralmeida.teste.shared.constants.validation.PessoaPerfilValidationMessages;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PessoaPerfilRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 5973184765774917436L;

  private UUID id;

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

  //  private @Valid List<PessoaPerfilAnexo> anexos = new ArrayList<>();

  public void validar(TipoPerfil tipoPerfil) {
    validarExistenciaDadosPessoais();
    validacoesTipoUsuario();
    contato.validar();

    boolean isCooperativaAssociacao = TipoPerfil.COOPERATIVA.equals(tipoPerfil) || TipoPerfil.ASSOCIACAO.equals(tipoPerfil);
    vinculo.validar(isCooperativaAssociacao);

    if (isCooperativaAssociacao) {
      validarCooperativaAssociacao();
    } else {
      if (presidente != null) {
        throw new NegocioException(PessoaPerfilValidationMessages.PRESIDENTE_NAO_PERMITIDO);
      }
    }
  }

  private void validarCooperativaAssociacao() {
    if (dadosPessoaJuridica == null) {
      throw new NegocioException(PessoaPerfilValidationMessages.DADOS_PESSOA_JURIDICA_OBRIGATORIO);
    }

    if (presidente == null) {
      throw new NegocioException(PessoaPerfilValidationMessages.PRESIDENTE_OBRIGATORIO);
    }

    dadosPessoaJuridica.validarCooperativaAssociacao();
    dadosPessoaFisica.validarDadosPresidente();
  }

  private void validacoesTipoUsuario() {
    if (TipoUsuario.PESSOA_FISICA.equals(tipoUsuario)) {
      validarCPF();
      validarDadosPessoaFisica();
    } else if (TipoUsuario.PESSOA_JURIDICA.equals(tipoUsuario)) {
      validarCNPJ();
      validarDadosPessoaJuridica();
    }
  }

  private void validarExistenciaDadosPessoais() {
    if (dadosPessoaFisica == null && dadosPessoaJuridica == null) {
      throw new NegocioException(PessoaPerfilValidationMessages.DADOS_PESSOAIS_OBRIGATORIOS);
    }
  }

  private void validarCNPJ() {
    if (documento.length() != SharedConstants.TAMANHO_CNPJ) {
      throw new NegocioException(PessoaPerfilValidationMessages.DOCUMENTO_CNPJ_TAMANHO);
    }
  }

  private void validarCPF() {
    if (documento.length() != SharedConstants.TAMANHO_CPF) {
      throw new NegocioException(PessoaPerfilValidationMessages.DOCUMENTO_CPF_TAMANHO);
    }
  }

  private void validarDadosPessoaJuridica() {
    if (dadosPessoaJuridica == null) {
      throw new NegocioException(PessoaPerfilValidationMessages.DADOS_PESSOA_JURIDICA_OBRIGATORIO);
    }
  }

  private void validarDadosPessoaFisica() {
    if (dadosPessoaFisica == null) {
      throw new NegocioException(PessoaPerfilValidationMessages.DADOS_PESSOA_FISICA_OBRIGATORIO);
    }
  }
}
