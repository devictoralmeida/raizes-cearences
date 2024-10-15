package com.devictoralmeida.teste.entities;

import com.devictoralmeida.teste.dto.request.VinculoRequestDto;
import com.devictoralmeida.teste.enums.RegistroSanitario;
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
@Table(name = "vinculo")
@NoArgsConstructor
public class Vinculo implements Serializable {
  @Serial
  private static final long serialVersionUID = 7002155743022020905L;

  @Id
  @GeneratedValue
  @Column(name = "id")
  private UUID id;

  @Column(name = "caf")
  private String caf;

  @Column(name = "fl_caf_juridica")
  private boolean isCafJuridica;

  @Column(name = "caf_juridica")
  private String cafJuridica;

  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  @Column(name = "dat_validade_caf")
  private LocalDate dataValidadeCaf;

  @Column(name = "fl_cadastro_secaf")
  private boolean isCadastroSecaf;

  @Column(name = "fl_servicos_ater")
  private boolean isServicosAter;

  @Column(name = "fl_oferta_ceasa")
  private boolean isOfertaCeasa;

  @Column(name = "fl_registro_sanitario")
  private boolean isRegistroSanitario;

  @Column(name = "fl_assistencia_tecnica")
  private boolean isAssistenciaTecnica;

  @Column(name = "registro_sanitario")
  @Enumerated(EnumType.STRING)
  private RegistroSanitario registroSanitario;

  public Vinculo(VinculoRequestDto request) {
    caf = request.getCaf();
    isCafJuridica = request.isCafJuridica();
    cafJuridica = request.getCafJuridica();
    dataValidadeCaf = request.getDataValidadeCaf();
    isCadastroSecaf = request.isCadastroSecaf();
    isServicosAter = request.isServicosAter();
    isOfertaCeasa = request.isOfertaCeasa();
    isRegistroSanitario = request.isRegistroSanitario();
    isAssistenciaTecnica = request.isAssistenciaTecnica();
    registroSanitario = request.getRegistroSanitario();
  }

}
