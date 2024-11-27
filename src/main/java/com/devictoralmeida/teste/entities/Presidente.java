package com.devictoralmeida.teste.entities;

import com.devictoralmeida.teste.dto.request.PresidenteRequestDto;
import com.devictoralmeida.teste.shared.auditoria.BaseAuditoria;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.io.Serial;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "presidente")
@Audited
@AuditTable("presidente_aud")
@NoArgsConstructor
public class Presidente extends BaseAuditoria {
  @Serial
  private static final long serialVersionUID = -6357371464647851857L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID id;

  @Column(name = "documento", nullable = false)
  private String documento;

  @Column(name = "dados_pessoa_id", nullable = false)
  private UUID dadosPessoaId;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "contato_id", referencedColumnName = "id", nullable = false)
  private Contato contato;

  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  @Column(name = "dat_inicio_mandato", nullable = false)
  private LocalDate dataInicioMandato;

  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  @Column(name = "dat_final_mandato", nullable = false)
  private LocalDate dataFinalMandato;

  @JsonIgnore
  @OneToOne
  @JoinColumn(name = "pessoa_perfil_id", referencedColumnName = "id", nullable = false)
  private PessoaPerfil pessoaPerfil;

  public Presidente(PresidenteRequestDto request, PessoaPerfil pessoaPerfil, UUID idDadosPessoaisPresidente) {
    documento = request.getDocumento();
    dataInicioMandato = request.getDataInicioMandato();
    dataFinalMandato = request.getDataFinalMandato();
    dadosPessoaId = idDadosPessoaisPresidente;
    this.pessoaPerfil = pessoaPerfil;
    contato = new Contato(request.getContato());
  }

}
