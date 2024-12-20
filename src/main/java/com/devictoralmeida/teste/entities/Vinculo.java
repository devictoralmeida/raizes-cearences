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
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "vinculo")
@Audited
@AuditTable("vinculo_aud")
@NoArgsConstructor
public class Vinculo extends BaseAuditoria {
  @Serial
  private static final long serialVersionUID = 7002155743022020905L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID id;

  @Column(name = "fl_cadastro_secaf", nullable = false)
  private boolean isCadastroSecaf = false;

  @Column(name = "fl_servicos_ater", nullable = false)
  private boolean isServicosAter = false;

  @Column(name = "fl_oferta_ceasa", nullable = false)
  private boolean isOfertaCeasa = false;

  public Vinculo(VinculoRequestDto request) {
    isCadastroSecaf = request.isCadastroSecaf();
    isServicosAter = request.isServicosAter();
    isOfertaCeasa = request.isOfertaCeasa();
  }

}
