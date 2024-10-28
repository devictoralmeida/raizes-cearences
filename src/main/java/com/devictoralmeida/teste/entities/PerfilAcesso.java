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
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "perfil_acesso")
@Audited
@AuditTable("perfil_acesso_aud")
public class PerfilAcesso extends BaseAuditoria implements Serializable {

  @Serial
  private static final long serialVersionUID = 8094849596093365365L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID id;

  @Column(name = "nome")
  private String nome;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "perfil_permissao",
          joinColumns = @JoinColumn(name = "perfil_acesso_id"),
          inverseJoinColumns = @JoinColumn(name = "permissao_id"))
  private Set<Permissao> permissoes = new HashSet<>();

  public String toStringMapper() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(this);
  }

}
