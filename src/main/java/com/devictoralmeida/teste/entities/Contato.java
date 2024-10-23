package com.devictoralmeida.teste.entities;

import com.devictoralmeida.teste.dto.request.ContatoRequestDto;
import com.devictoralmeida.teste.enums.TipoContato;
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
@Table(name = "contato")
@Audited
@AuditTable("contato_aud")
@NoArgsConstructor
public class Contato extends BaseAuditoria implements Serializable {
  @Serial
  private static final long serialVersionUID = 1479232788245807261L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID id;

  @Column(name = "preferencia_contato")
  @Enumerated(EnumType.STRING)
  private TipoContato preferenciaContato;

  @Column(name = "numero_contato")
  private String numeroContato;

  @Column(name = "numero_whatsapp")
  private String numeroWhatsapp;

  @Column(name = "email")
  private String email;

  @Column(name = "fl_whatsapp")
  private boolean isWhatsapp;

  @Column(name = "fl_newsletter")
  private boolean isNewsletter;

  public Contato(ContatoRequestDto request) {
    preferenciaContato = request.getPreferenciaContato();
    numeroContato = request.getNumeroContato();
    numeroWhatsapp = request.getNumeroWhatsapp();
    email = request.getEmail();
    isWhatsapp = request.isWhatsapp();
    isNewsletter = request.isNewsletter();
  }
}
