package com.devictoralmeida.teste.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "codigo_verificacao")
public class CodigoVerificacao implements Serializable {
  @Serial
  private static final long serialVersionUID = -4375495652608782563L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID id;

  @Column(name = "codigo", nullable = false)
  private String codigo;

  @Column(name = "fl_validado")
  private boolean isValido = false;

  @Column(name = "dat_expiracao", nullable = false)
  @JsonIgnore
  private LocalDateTime dataExpiracao;

  public CodigoVerificacao(String codigo, LocalDateTime dataExpiracao) {
    this.codigo = codigo;
    this.dataExpiracao = dataExpiracao;
  }
}
