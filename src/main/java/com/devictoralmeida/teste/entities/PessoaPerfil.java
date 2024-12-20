package com.devictoralmeida.teste.entities;

import com.devictoralmeida.teste.dto.request.PessoaPerfilRequestDto;
import com.devictoralmeida.teste.enums.TipoUsuario;
import com.devictoralmeida.teste.shared.auditoria.BaseAuditoria;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "pessoa_perfil")
@Getter
@Setter
@Entity
@NoArgsConstructor
@Audited
@AuditTable("pessoa_perfil_aud")
public class PessoaPerfil extends BaseAuditoria {
  @Serial
  private static final long serialVersionUID = -7349150015441550944L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID id;

  @Column(name = "tipo_usuario", nullable = false)
  @Enumerated(EnumType.STRING)
  private TipoUsuario tipoUsuario;

  @Column(name = "documento", nullable = false, unique = true)
  private String documento;

  @Column(name = "dados_pessoa_id", nullable = false)
  private UUID dadosPessoaId;

  @JsonIgnore
  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private Usuario usuario;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "vinculo_id", referencedColumnName = "id", nullable = false)
  private Vinculo vinculo;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "contato_id", referencedColumnName = "id", nullable = false)
  private Contato contato;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "endereco_id", referencedColumnName = "id", nullable = false)
  private Endereco endereco;

  @OneToOne(cascade = CascadeType.ALL, mappedBy = "pessoaPerfil")
  private Presidente presidente;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "pessoaPerfil")
  private List<PessoaPerfilAnexo> pessoaPerfilAnexo = new ArrayList<>();

  public PessoaPerfil(PessoaPerfilRequestDto request, Usuario usuario, UUID dadosPessoaId) {
    this(request, usuario, dadosPessoaId, null);
  }

  public PessoaPerfil(PessoaPerfilRequestDto request, Usuario usuario, UUID dadosPessoaId, UUID idDadosPessoaisPresidente) {
    tipoUsuario = request.getTipoUsuario();
    documento = request.getDocumento();
    this.dadosPessoaId = dadosPessoaId;
    this.usuario = usuario;
    vinculo = new Vinculo(request.getVinculo());
    contato = new Contato(request.getContato());
    endereco = new Endereco(request.getEndereco());

    if (idDadosPessoaisPresidente != null) {
      presidente = new Presidente(request.getPresidente(), this, idDadosPessoaisPresidente);
    }
  }
}
