package com.devictoralmeida.teste.entities;

import com.devictoralmeida.teste.dto.request.UsuarioRequestDto;
import com.devictoralmeida.teste.enums.TipoPerfil;
import com.devictoralmeida.teste.shared.auditoria.BaseAuditoria;
import com.devictoralmeida.teste.shared.utils.PermissoesUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "usuario")
@Audited
@AuditTable("usuario_aud")
public class Usuario extends BaseAuditoria implements UserDetails {
  @Serial
  private static final long serialVersionUID = 5017933890529895923L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID id;

  @Column(name = "login", nullable = false, unique = true)
  private String login;

  @Column(name = "tipo_perfil", nullable = false)
  @Enumerated(EnumType.STRING)
  private TipoPerfil tipoPerfil;

  @NotAudited
  @Column(name = "firebase_uid", nullable = false, unique = true)
  private String firebaseUID;

  @JsonIgnore
  @OneToOne
  @NotAudited
  @JoinColumn(name = "codigo_verificacao")
  private CodigoVerificacao codigoVerificacao;

  @NotAudited
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @Column(name = "dat_aceite_termo")
  private LocalDateTime dataAceiteTermo;

  @OneToOne(cascade = CascadeType.ALL, mappedBy = "usuario")
  private PessoaPerfil pessoaPerfil;

  @NotAudited
  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "termo_id", referencedColumnName = "id")
  private TermoCondicao termo;

  @JsonIgnore
  @ManyToMany
  @JoinTable(name = "usuario_perfil_acesso",
          joinColumns = @JoinColumn(name = "usuario_id"),
          inverseJoinColumns = @JoinColumn(name = "perfil_acesso_id"))
  private Set<PerfilAcesso> perfisAcessos = new HashSet<>();

  public Usuario(UsuarioRequestDto request, TermoCondicao termoCondicao) {
    login = request.getLogin();
    tipoPerfil = request.getTipoPerfil();
    termo = termoCondicao;

    // Lembrar de apagar as linhas abaixo
    dataAceiteTermo = LocalDateTime.now();
  }

  public boolean possuiPermissao(String permissao) {
    return getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals(permissao));
  }

  @JsonIgnore
  public boolean isAdmin() {
    return TipoPerfil.ADMINISTRADOR.equals(tipoPerfil);
  }

  @JsonIgnore
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return PermissoesUtils.getPermissoes(perfisAcessos);
  }

  @JsonIgnore
  @Override
  public String getPassword() {
    return null;
  }

  @JsonIgnore
  @Override
  public String getUsername() {
    return login;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @JsonIgnore
  @Override
  public boolean isEnabled() {
    return true;
  }

  public String toStringMapper() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    return objectMapper.writeValueAsString(this);
  }
}
