package com.devictoralmeida.teste.entities;

import com.devictoralmeida.teste.shared.auditoria.BaseAuditoria;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "termo_condicao")
public class TermoCondicao extends BaseAuditoria {
  @Serial
  private static final long serialVersionUID = -13935003319858381L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID id;

  @Column(name = "versao")
  private String versao;

  @Column(name = "conteudo")
  private String conteudo;

  @OneToMany(mappedBy = "termo")
  private List<Usuario> usuarios;
}
