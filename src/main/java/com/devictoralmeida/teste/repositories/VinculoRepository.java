package com.devictoralmeida.teste.repositories;

import com.devictoralmeida.teste.entities.Vinculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VinculoRepository extends JpaRepository<Vinculo, UUID> {
}
