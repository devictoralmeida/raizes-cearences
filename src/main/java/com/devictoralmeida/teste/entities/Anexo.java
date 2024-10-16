package com.devictoralmeida.teste.entities;

import com.devictoralmeida.teste.dto.request.AnexoRequestDto;
import com.devictoralmeida.teste.shared.auditoria.BaseAuditoria;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "anexo")
@Audited
@AuditTable("anexo_aud")
@NoArgsConstructor
public class Anexo extends BaseAuditoria implements Serializable {
  @Serial
  private static final long serialVersionUID = 5255144870691782603L;

  @Id
  @GeneratedValue
  @Column(name = "id")
  private UUID id;

  @Column(name = "nome_anexo", nullable = false)
  private String nome;

  @Column(name = "tipo_mime", nullable = false, length = 5)
  private String tipo;

  @JsonIgnore
  @NotAudited
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "anexo")
  private List<PessoaPerfilAnexo> pessoaPerfilAnexos = new ArrayList<>();

  public Anexo(AnexoRequestDto request) {
    nome = request.getNome();
    tipo = request.getTipo();
  }
}
