package com.devictoralmeida.teste.entities;

import com.devictoralmeida.teste.dto.request.UsuarioRequestDto;
import com.devictoralmeida.teste.enums.TipoPerfil;
import com.devictoralmeida.teste.shared.auditoria.BaseAuditoria;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "usuario")
@Audited
@AuditTable("usuario_aud")
public class Usuario extends BaseAuditoria implements UserDetails, Serializable {
  @Serial
  private static final long serialVersionUID = 5017933890529895923L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID id;

  @Column(name = "login", nullable = false)
  private String login;

  @Column(name = "tipo_perfil", nullable = false)
  @Enumerated(EnumType.STRING)
  private TipoPerfil tipoPerfil;

  @NotAudited
  @Column(name = "firebase_uid", nullable = false)
  private String firebaseUID;

  @JsonIgnore
  @Column(name = "senha")
  private String senha;

  @JsonIgnore
  @OneToOne
  @NotAudited
  @JoinColumn(name = "codigo_verificacao")
  private CodigoVerificacao codigoVerificacao;

  @OneToOne(cascade = CascadeType.ALL, mappedBy = "usuario")
  private PessoaPerfil pessoaPerfil;

  @ManyToMany
  @JoinTable(name = "usuario_perfil_acesso",
          joinColumns = @JoinColumn(name = "usuario_id"),
          inverseJoinColumns = @JoinColumn(name = "perfil_acesso_id"))
  private List<PerfilAcesso> perfisAcessos;

  public Usuario(UsuarioRequestDto request, String senha) {
    login = request.getLogin();
    tipoPerfil = request.getTipoPerfil();
    this.senha = senha;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }

  @Override
  public String getPassword() {
    return senha;
  }

  @Override
  public String getUsername() {
    return login;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
