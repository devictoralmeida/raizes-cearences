package com.devictoralmeida.teste.services;

import com.devictoralmeida.teste.dto.request.LoginRequestDto;
import com.google.firebase.auth.FirebaseAuthException;

public interface AuthService {
  String login(LoginRequestDto request) throws FirebaseAuthException;
}
