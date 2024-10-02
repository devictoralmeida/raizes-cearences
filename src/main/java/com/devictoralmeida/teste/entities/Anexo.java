package com.devictoralmeida.teste.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "anexo")
@NoArgsConstructor
public class Anexo implements Serializable {
  @Serial
  private static final long serialVersionUID = 5255144870691782603L;

  @Id
  @GeneratedValue
  @Column(name = "id")
  private UUID id;

  @Column(name = "nome_anexo", nullable = false)
  private String nomeAnexo;

  @Column(name = "tipo_mime", nullable = false, length = 5)
  private String tipoMime;

  @JsonIgnore
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "anexo")
  private List<PessoaPerfilAnexo> pessoaPerfilAnexos = new ArrayList<>();

}
