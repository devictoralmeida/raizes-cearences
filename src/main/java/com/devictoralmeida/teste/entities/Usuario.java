package com.devictoralmeida.teste.entities;

import com.devictoralmeida.teste.dto.request.UsuarioRequestDto;
import com.devictoralmeida.teste.enums.TipoPerfil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

  @Column(name = "codigo_verificacao")
  private String codigoVerificacao;

  @OneToOne(cascade = CascadeType.ALL, mappedBy = "usuario")
  private PessoaPerfil pessoaPerfil;

  public Usuario(UsuarioRequestDto request) {
    login = "anônimo";
    tipoPerfil = request.getTipoPerfil();
    firebaseUID = UUID.randomUUID().toString().substring(0, 28);
  }
}
