package com.devictoralmeida.teste.dto.request;

import com.devictoralmeida.teste.enums.TipoDocumento;
import com.devictoralmeida.teste.shared.constants.validation.AnexoValidationMessages;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

  @Size(max = 255, message = AnexoValidationMessages.NOME_ANEXO_TAMANHO)
  private String nome;

  @NotNull(message = AnexoValidationMessages.TIPO_DOCUMENTO_OBRIGATORIO)
  private TipoDocumento tipoDocumento;

  @NotEmpty(message = AnexoValidationMessages.ARQUIVO_OBRIGATORIO)
  private transient MultipartFile arquivo;

  @Size(max = 5, message = AnexoValidationMessages.TIPO_MIME_TAMANHO)
  private String tipo = FilenameUtils.getExtension(arquivo.getOriginalFilename());

  public void validar() {
    verificaTipoArquivo();
  }

  private void verificaTipoArquivo() {
    List<String> formatosPermitidos = List.of("pdf", "png", "doc", "docx", "odt");

    if (!formatosPermitidos.contains(tipo)) {
      throw new NegocioException(AnexoValidationMessages.TIPO_INVALIDO);
    }
  }
}
