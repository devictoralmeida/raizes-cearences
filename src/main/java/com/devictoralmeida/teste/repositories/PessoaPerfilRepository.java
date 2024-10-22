package com.devictoralmeida.teste.repositories;

import com.devictoralmeida.teste.entities.PessoaPerfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PessoaPerfilRepository extends JpaRepository<PessoaPerfil, UUID> {

}
