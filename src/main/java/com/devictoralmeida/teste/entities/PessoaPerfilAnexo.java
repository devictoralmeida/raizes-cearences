package com.devictoralmeida.teste.entities;

import com.devictoralmeida.teste.dto.request.AnexoRequestDto;
import com.devictoralmeida.teste.enums.TipoDocumento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "pessoa_perfil_anexo")
public class PessoaPerfilAnexo implements Serializable {
  @Serial
  private static final long serialVersionUID = -1476036932706673337L;

  @Id
  @GeneratedValue
  @Column(name = "id")
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "id_pessoa_perfil", nullable = false)
  private PessoaPerfil pessoaPerfil;

  @ManyToOne
  @JoinColumn(name = "id_anexo", nullable = false)
  private Anexo anexo;

  @Column(name = "tipo_documento", nullable = false)
  @Enumerated(EnumType.STRING)
  private TipoDocumento tipoDocumento;

  public PessoaPerfilAnexo(AnexoRequestDto request, Usuario usuario) {
    anexo = new Anexo(request);
    tipoDocumento = request.getTipoDocumento();
    pessoaPerfil = usuario.getPessoaPerfil();
  }

}
