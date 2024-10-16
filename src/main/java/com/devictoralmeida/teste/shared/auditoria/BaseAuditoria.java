package com.devictoralmeida.teste.shared.auditoria;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class BaseAuditoria implements Serializable {
  @Serial
  private static final long serialVersionUID = -3671068621462712178L;

  private static final String ANONIMO = "anônimo";

  @JsonIgnore
  @Column(name = "dat_criacao", nullable = false, updatable = false)
  @CreatedDate
  private LocalDateTime dataCriacao;

  @JsonIgnore
  @Column(name = "dat_atualizacao", insertable = false)
  @LastModifiedDate
  private LocalDateTime dataAtualizacao;

  @JsonIgnore
  @Column(name = "nm_usuario_cadastro", updatable = false)
  @CreatedBy
  private String nomeUsuarioCadastro;

  @JsonIgnore
  @Column(name = "nm_usuario_atualizacao", insertable = false)
  @LastModifiedBy
  private String nomeUsuarioAtualizacao;

  @PreUpdate
  public void preUpdate() {
    setNomeUsuarioAtualizacao(getNomeUsuario());
  }

  @PrePersist
  public void prePersist() {
    setNomeUsuarioCadastro(getNomeUsuario());
  }

  private String getNomeUsuario() {
    return ANONIMO;
  }

}
