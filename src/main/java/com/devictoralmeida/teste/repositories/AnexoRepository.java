package com.devictoralmeida.teste.repositories;

import com.devictoralmeida.teste.entities.Anexo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AnexoRepository extends JpaRepository<Anexo, UUID> {
}
