package com.devictoralmeida.teste.entities;

import com.devictoralmeida.teste.dto.request.DadosPessoaFisicaRequestDto;
import com.devictoralmeida.teste.enums.GrauInstrucao;
import com.devictoralmeida.teste.enums.Sexo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "dados_pessoa_fisica")
@NoArgsConstructor
public class DadosPessoaFisica implements Serializable {
  @Serial
  private static final long serialVersionUID = -9139757556305789780L;

  @Id
  @GeneratedValue
  @Column(name = "id")
  private UUID id;

  @Column(name = "rg", nullable = false)
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

  @Column(name = "grau_instrucao", nullable = false)
  @Enumerated(EnumType.STRING)
  private GrauInstrucao grauInstrucao;

  @Column(name = "nome_mae")
  private String nomeMae;

  @Column(name = "nome_pai")
  private String nomePai;

  public DadosPessoaFisica(DadosPessoaFisicaRequestDto request) {
    rg = request.getRg();
    orgaoExpeditor = request.getOrgaoExpeditor();
    dataExpedicao = request.getDataExpedicao();
    nome = request.getNome();
    sobrenome = request.getSobrenome();
    dataNascimento = request.getDataNascimento();
    sexo = request.getSexo();
    grauInstrucao = request.getGrauInstrucao();
    nomeMae = request.getNomeMae();
    nomePai = request.getNomePai();
  }
}
