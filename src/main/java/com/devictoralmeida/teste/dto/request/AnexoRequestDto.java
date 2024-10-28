package com.devictoralmeida.teste.dto.request;

import com.devictoralmeida.teste.enums.TipoDocumento;
import com.devictoralmeida.teste.shared.constants.SharedConstants;
import com.devictoralmeida.teste.shared.constants.validation.AnexoValidationMessages;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class AnexoRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 415021909937948108L;

  private String nome;

  private TipoDocumento tipoDocumento;

  private transient MultipartFile arquivo;

  @Size(max = 5, message = AnexoValidationMessages.TIPO_MIME_TAMANHO)
  private String tipo;

  public AnexoRequestDto(TipoDocumento tipoDocumento, MultipartFile arquivo) {
    this.tipoDocumento = tipoDocumento;
    this.arquivo = arquivo;
    tipo = FilenameUtils.getExtension(arquivo.getOriginalFilename());
  }

  public void validar() {
    verificaTipoArquivo();
    verificaTamanhoNome();
  }

  private void verificaTamanhoNome() {
    if (arquivo.getOriginalFilename() != null && arquivo.getOriginalFilename().length() > SharedConstants.TAMANHO_MAXIMO_DOMINIO_EMAIL) {
      throw new NegocioException(AnexoValidationMessages.NOME_ANEXO_TAMANHO);
    }
  }

  private void verificaTipoArquivo() {
    List<String> formatosPermitidos = List.of("pdf");

    if (!formatosPermitidos.contains(tipo)) {
      throw new NegocioException(AnexoValidationMessages.TIPO_INVALIDO);
    }
  }
}
