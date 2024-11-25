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

  Optional<Usuario> findByLogin(String login);

  @Query("SELECT obj FROM Usuario obj JOIN FETCH obj.perfisAcessos pa JOIN FETCH obj.pessoaPerfil pp LEFT JOIN FETCH pp.pessoaPerfilAnexo WHERE obj.id = :id")
  Usuario getByIdComAnexos(UUID id);

  @Query("SELECT obj FROM Usuario obj JOIN FETCH obj.perfisAcessos pa JOIN FETCH obj.pessoaPerfil pp LEFT JOIN FETCH pp.pessoaPerfilAnexo WHERE obj.login = :login")
  Usuario getUsuarioLogadoByLogin(String login);

  @Query("SELECT obj FROM Usuario obj JOIN FETCH  obj.perfisAcessos WHERE obj.firebaseUID = :uid")
  Optional<Usuario> findByFirebaseUID(String uid);
}
