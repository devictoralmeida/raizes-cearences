package com.devictoralmeida.teste.entities;

import com.devictoralmeida.teste.dto.request.VinculoRequestDto;
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
@Table(name = "vinculo")
@Audited
@AuditTable("vinculo_aud")
@NoArgsConstructor
public class Vinculo extends BaseAuditoria implements Serializable {
  @Serial
  private static final long serialVersionUID = 7002155743022020905L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID id;

  @Column(name = "fl_cadastro_secaf")
  private boolean isCadastroSecaf;

  @Column(name = "fl_servicos_ater")
  private boolean isServicosAter;

  @Column(name = "fl_oferta_ceasa")
  private boolean isOfertaCeasa;

  public Vinculo(VinculoRequestDto request) {
    isCadastroSecaf = request.isCadastroSecaf();
    isServicosAter = request.isServicosAter();
    isOfertaCeasa = request.isOfertaCeasa();
  }

}
