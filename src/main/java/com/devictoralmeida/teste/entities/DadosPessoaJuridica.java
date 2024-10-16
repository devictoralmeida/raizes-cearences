package com.devictoralmeida.teste.entities;

import com.devictoralmeida.teste.dto.request.DadosPessoaJuridicaRequestDto;
import com.devictoralmeida.teste.shared.auditoria.BaseAuditoria;
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
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "dados_pessoa_juridica")
@Audited
@AuditTable("dados_pessoa_juridica_aud")
@NoArgsConstructor
public class DadosPessoaJuridica extends BaseAuditoria implements Serializable {
  @Serial
  private static final long serialVersionUID = -878915797050051145L;

  @Id
  @GeneratedValue
  @Column(name = "id")
  private UUID id;

  @Column(name = "sigla")
  private String sigla;

  @Column(name = "razao_social", nullable = false)
  private String razaoSocial;

  @Column(name = "nome_fantasia")
  private String nomeFantasia;

  @Column(name = "inscricao_junta_comercial")
  private String inscricaoJuntaComercial;

  @Column(name = "inscricao_estadual")
  private String inscricaoEstadual;

  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  @Column(name = "dat_fundacao")
  private LocalDate dataFundacao;

  public DadosPessoaJuridica(DadosPessoaJuridicaRequestDto request) {
    sigla = request.getSigla();
    razaoSocial = request.getRazaoSocial();
    nomeFantasia = request.getNomeFantasia();
    inscricaoJuntaComercial = request.getInscricaoJuntaComercial();
    inscricaoEstadual = request.getInscricaoEstadual();
    dataFundacao = request.getDataFundacao();
  }
}
