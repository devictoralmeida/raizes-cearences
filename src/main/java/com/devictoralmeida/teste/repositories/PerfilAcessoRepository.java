package com.devictoralmeida.teste.repositories;

import com.devictoralmeida.teste.entities.PerfilAcesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PerfilAcessoRepository extends JpaRepository<PerfilAcesso, UUID> {
  @Query(value = "SELECT * FROM perfil_acesso p WHERE LOWER(p.nome) LIKE LOWER('%oferta%')", nativeQuery = true)
  List<PerfilAcesso> getPerfilAcessoOfertasPlanos();
}
