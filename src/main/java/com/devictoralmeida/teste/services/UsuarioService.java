package com.devictoralmeida.teste.services;

import com.devictoralmeida.teste.dto.request.AnexoRequestDto;
import com.devictoralmeida.teste.dto.request.UsuarioRequestDto;
import com.devictoralmeida.teste.entities.Usuario;
import com.devictoralmeida.teste.enums.TipoContato;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface UsuarioService extends UserDetailsService {
  void save(@Valid UsuarioRequestDto request);

  void uploadAnexos(String login, List<AnexoRequestDto> anexos);

  Usuario findById(UUID id);

  Usuario findByLogin(String login);

  Usuario findByFirebaseUID(String uid);

  void validarCodigo(String login, String codigo);

  void reenviarCodigo(String login, TipoContato canalEnvio);

  void delete(UUID id);

  FirebaseToken verificarToken(String idToken) throws FirebaseAuthException;
}
