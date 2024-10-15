package com.devictoralmeida.teste.entities;

import com.devictoralmeida.teste.dto.request.UsuarioRequestDto;
import com.devictoralmeida.teste.enums.TipoPerfil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.NotAudited;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "usuario")
public class Usuario implements Serializable {
  @Serial
  private static final long serialVersionUID = 5017933890529895923L;

  @Id
  @GeneratedValue
  @Column(name = "id")
  private UUID id;

  @Column(name = "login", nullable = false)
  private String login;

  @Column(name = "tipo_perfil", nullable = false)
  @Enumerated(EnumType.STRING)
  private TipoPerfil tipoPerfil;

  @Column(name = "firebase_uid", nullable = false)
  private String firebaseUID;

  @JsonIgnore
  @OneToOne
  @NotAudited
  @JoinColumn(name = "codigo_verificacao")
  private CodigoVerificacao codigoVerificacao;

  @JsonIgnore
  @Column(name = "senha")
  private String senha;

  @OneToOne(cascade = CascadeType.ALL, mappedBy = "usuario")
  private PessoaPerfil pessoaPerfil;

  public Usuario(UsuarioRequestDto request, String senha) {
    login = request.getLogin();
    tipoPerfil = request.getTipoPerfil();
    this.senha = senha;
  }
}
