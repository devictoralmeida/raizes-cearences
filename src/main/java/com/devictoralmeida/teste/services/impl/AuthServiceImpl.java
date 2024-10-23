package com.devictoralmeida.teste.services.impl;

import com.devictoralmeida.teste.dto.request.LoginRequestDto;
import com.devictoralmeida.teste.entities.Usuario;
import com.devictoralmeida.teste.enums.TipoPerfil;
import com.devictoralmeida.teste.services.AuthService;
import com.devictoralmeida.teste.services.UsuarioService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final UsuarioService usuarioService;
  private final FirebaseAuth firebaseAuth;

  @Override
  public String login(LoginRequestDto request) throws FirebaseAuthException {
    Usuario usuario = usuarioService.findByLogin(request.getLogin());
    if (usuario.getPessoaPerfil().getContato().getEmail() != null) {
      return null;
    }
    return null;
  }

  @Override
  public List<GrantedAuthority> criarPermissoes(String uid) {
    Usuario usuario = usuarioService.findByFirebaseUID(uid);
    TipoPerfil tipoPerfil = usuario.getTipoPerfil();

    if (TipoPerfil.AGRICULTOR.equals(tipoPerfil)) {
      return List.of(() -> "AGRICULTOR");
    } else if (TipoPerfil.COOPERATIVA.equals(tipoPerfil)) {
      return List.of(() -> "COOPERATIVA");
    } else if (TipoPerfil.ASSOCIACAO.equals(tipoPerfil)) {
      return List.of(() -> "ASSOCIACAO");
    } else if (TipoPerfil.CONSUMIDOR.equals(tipoPerfil)) {
      return List.of(() -> "CONSUMIDOR");
    } else {
      return List.of(() -> "ADMIN");
    }
  }
}