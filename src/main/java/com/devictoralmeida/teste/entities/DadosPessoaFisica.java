package com.devictoralmeida.teste.entities;

import com.devictoralmeida.teste.dto.request.DadosPessoaFisicaRequestDto;
import com.devictoralmeida.teste.enums.GrauInstrucao;
import com.devictoralmeida.teste.enums.Sexo;
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
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "dados_pessoa_fisica")
@Audited
@AuditTable("dados_pessoa_fisica_aud")
@NoArgsConstructor
public class DadosPessoaFisica extends BaseAuditoria {
  @Serial
  private static final long serialVersionUID = -9139757556305789780L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID id;

  @Column(name = "rg", nullable = false, unique = true)
  private String rg;

  @Column(name = "orgao_expeditor")
  private String orgaoExpeditor;

  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  @Column(name = "dat_expedicao")
  private LocalDate dataExpedicao;

  @Column(name = "nome", nullable = false)
  private String nome;

  @Column(name = "sobrenome", nullable = false)
  private String sobrenome;

  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  @Column(name = "dat_nascimento", nullable = false)
  private LocalDate dataNascimento;

  @Column(name = "sexo", nullable = false)
  @Enumerated(EnumType.STRING)
  private Sexo sexo;

  @Column(name = "grau_instrucao")
  @Enumerated(EnumType.STRING)
  private GrauInstrucao grauInstrucao;

  public DadosPessoaFisica(DadosPessoaFisicaRequestDto request) {
    rg = request.getRg();
    orgaoExpeditor = request.getOrgaoExpeditor();
    dataExpedicao = request.getDataExpedicao();
    nome = request.getNome().trim();
    sobrenome = request.getSobrenome().trim();
    dataNascimento = request.getDataNascimento();
    sexo = request.getSexo();
    grauInstrucao = request.getGrauInstrucao();
  }
}
