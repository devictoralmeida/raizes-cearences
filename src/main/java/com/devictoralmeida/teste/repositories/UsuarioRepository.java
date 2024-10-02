package com.devictoralmeida.teste.repositories;

import com.devictoralmeida.teste.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
  @Query(value = "select * from usuario order by dat_criacao desc limit 1", nativeQuery = true)
  Usuario obterUltimo();
}
