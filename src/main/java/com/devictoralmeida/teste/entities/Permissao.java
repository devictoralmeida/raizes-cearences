package com.devictoralmeida.teste.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "permissao")
@ToString
public class Permissao implements Serializable {
  @Serial
  private static final long serialVersionUID = 7901047435748597530L;

  @Id
  @GeneratedValue
  @Column(name = "id")
  private UUID id;

  @Column(name = "nome")
  private String nome;

  @Column(name = "modulo_id")


  public String toStringMapper() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Permissao permissao = (Permissao) obj;
    return Objects.equals(id, permissao.id) && Objects.equals(nome, permissao.nome);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nome);
  }

}
