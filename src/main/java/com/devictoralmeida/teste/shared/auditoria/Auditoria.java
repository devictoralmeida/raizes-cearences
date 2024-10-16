package com.devictoralmeida.teste.shared.auditoria;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_auditoria")
@RevisionEntity(CustomRevisionListener.class)
@Getter
@Setter
@ToString(callSuper = true)
public class Auditoria {

  @Id
  @RevisionNumber
  @GeneratedValue
  @Column(name = "ci_auditoria", nullable = false)
  private Long id;

  @RevisionTimestamp
  @Column(name = "nr_timestamp")
  @JsonIgnore
  private long timestamp;

  @Column(name = "dt_movimento")
  @JsonIgnore
  private LocalDateTime dataMovimento;

  @Column(name = "nm_usuario")
  private String nomeUsuario;

  @Column(name = "ds_dados_antigos", length = 2048)
  private String dadosAntigos;

}
