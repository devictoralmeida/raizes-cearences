package com.devictoralmeida.teste.dto.request;

import com.devictoralmeida.teste.enums.TipoDocumento;
import com.devictoralmeida.teste.shared.constants.validation.AnexoValidationMessages;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class AnexoRequestDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 415021909937948108L;

  private String nome;

  private TipoDocumento tipoDocumento;

  private transient MultipartFile anexo;

  @Size(max = 5, message = AnexoValidationMessages.TIPO_MIME_TAMANHO)
  private String tipo;

  public AnexoRequestDto(TipoDocumento tipoDocumento, MultipartFile anexo) {
    this.tipoDocumento = tipoDocumento;
    this.anexo = anexo;
    tipo = FilenameUtils.getExtension(anexo.getOriginalFilename());
  }
}
