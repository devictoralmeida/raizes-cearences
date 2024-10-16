package com.devictoralmeida.teste.shared.auditoria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.envers.RevisionListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CustomRevisionListener implements RevisionListener {
  private static String dadosAntigos;
  private static String nomeUsuario;

  @PersistenceContext
  private EntityManager entityManager;

  public static synchronized String getDadosAntigos() {
    return dadosAntigos;
  }

  public static synchronized void setDadosAntigos(String dadosAntigos) {
    CustomRevisionListener.dadosAntigos = dadosAntigos;
  }

  public static String getNomeUsuario() {
    return nomeUsuario;
  }

  public static void setNomeUsuario(String nomeUsuario) {
    CustomRevisionListener.nomeUsuario = nomeUsuario;
  }

  @Override
  public void newRevision(Object entity) {
    Auditoria revision = (Auditoria) entity;
    revision.setDataMovimento(LocalDateTime.now());
    revision.setNomeUsuario(nomeUsuario == null ? "anônimo" : nomeUsuario);
    revision.setDadosAntigos(dadosAntigos);

    if (entityManager != null) {
      entityManager.flush();
    }

    setDadosAntigos(null);
  }
}
