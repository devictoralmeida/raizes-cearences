package com.devictoralmeida.teste.repositories;

import com.devictoralmeida.teste.entities.TermoCondicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TermoCondicaoRepository extends JpaRepository<TermoCondicao, UUID> {
  @Query("SELECT t FROM TermoCondicao t ORDER BY t.dataAtualizacao DESC")
  TermoCondicao findLatest();
}
