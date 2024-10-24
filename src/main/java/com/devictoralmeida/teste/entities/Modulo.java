package com.devictoralmeida.teste.entities;

import com.devictoralmeida.teste.shared.auditoria.BaseAuditoria;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "modulo")
@Audited
@AuditTable("modulo_aud")
public class Modulo extends BaseAuditoria implements Serializable {
  @Serial
  private static final long serialVersionUID = 7901047435748597530L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID id;

  @Column(name = "nome")
  private String nome;

  @ManyToMany
  @JoinTable(name = "modulo_permissao",
          joinColumns = @JoinColumn(name = "modulo_id"),
          inverseJoinColumns = @JoinColumn(name = "permissao_id"))
  private List<Permissao> permissoes;

  public String toStringMapper() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(this);
  }

}
