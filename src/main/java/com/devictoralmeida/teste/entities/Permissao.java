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
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "permissao")
@Audited
@AuditTable("permissao_aud")
public class Permissao extends BaseAuditoria implements Serializable {
  @Serial
  private static final long serialVersionUID = 7901047435748597530L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID id;

  @Column(name = "nome")
  private String nome;

  public String toStringMapper() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(this);
  }

}
