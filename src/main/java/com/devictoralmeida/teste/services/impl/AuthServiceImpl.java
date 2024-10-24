package com.devictoralmeida.teste.services.impl;

import com.devictoralmeida.teste.dto.request.LoginRequestDto;
import com.devictoralmeida.teste.entities.Usuario;
import com.devictoralmeida.teste.services.AuthService;
import com.devictoralmeida.teste.services.UsuarioService;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final UsuarioService usuarioService;

  @Override
  public String login(LoginRequestDto request) throws FirebaseAuthException {
    Usuario usuario = usuarioService.findByLogin(request.getLogin());

//    if (usuario.getPessoaPerfil().getContato().getEmail() != null) {
//      return null;
//    }

    return null;
  }
}