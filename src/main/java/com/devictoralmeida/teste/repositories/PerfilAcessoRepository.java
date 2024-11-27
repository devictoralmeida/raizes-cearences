package com.devictoralmeida.teste.repositories;

import com.devictoralmeida.teste.entities.PerfilAcesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PerfilAcessoRepository extends JpaRepository<PerfilAcesso, UUID> {
}
