package com.devictoralmeida.teste.entities;

import com.devictoralmeida.teste.dto.request.EnderecoRequestDto;
import com.devictoralmeida.teste.shared.auditoria.BaseAuditoria;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "endereco")
@Audited
@AuditTable("endereco_aud")
@NoArgsConstructor
public class Endereco extends BaseAuditoria implements Serializable {
  @Serial
  private static final long serialVersionUID = 8519377956416295703L;

  @Id
  @GeneratedValue
  @Column(name = "id")
  private UUID id;

  @Column(name = "cep", nullable = false)
  private String cep;

  @Column(name = "municipio", nullable = false)
  private String municipio;

  @Column(name = "distrito", nullable = false)
  private String distrito;

  @Column(name = "localidade", nullable = false)
  private String localidade;

  @Column(name = "logradouro", nullable = false)
  private String logradouro;

  @Column(name = "numero", nullable = false)
  private String numero;

  @Column(name = "complemento")
  private String complemento;

  @Column(name = "bairro")
  private String bairro;

  @Column(name = "uf", nullable = false)
  private String uf;

  @Column(name = "ponto_referencia")
  private String pontoReferencia;

  public Endereco(EnderecoRequestDto request) {
    cep = request.getCep();
    municipio = request.getMunicipio();
    distrito = request.getDistrito();
    localidade = request.getLocalidade();
    logradouro = request.getLogradouro();
    numero = request.getNumero();
    complemento = request.getComplemento();
    bairro = request.getBairro();
    uf = request.getUf();
    pontoReferencia = request.getPontoReferencia();
  }
}
