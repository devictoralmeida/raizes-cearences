package com.devictoralmeida.teste.repositories;

import com.devictoralmeida.teste.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
  @Query("SELECT u FROM Usuario u JOIN u.pessoaPerfil pp JOIN pp.contato c WHERE c.email = :email")
  Optional<Usuario> findByEmail(String email);

  @Query("SELECT u FROM Usuario u JOIN u.pessoaPerfil pp JOIN pp.contato c WHERE c.numeroWhatsapp = :whatsapp")
  Optional<Usuario> findByWhatsapp(String whatsapp);

  @Query(value = "select * from usuario u where u.login = :login", nativeQuery = true)
  Optional<Usuario> findByLogin(String login);
}
