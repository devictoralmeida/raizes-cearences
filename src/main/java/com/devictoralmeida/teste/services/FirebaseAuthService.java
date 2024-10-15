package com.devictoralmeida.teste.services;

import com.devictoralmeida.teste.dto.request.UsuarioRequestDto;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

public interface FirebaseAuthService {
  UserRecord criarUsuarioFirebase(UsuarioRequestDto dto) throws FirebaseAuthException;

  void emailVerificado(String uid) throws FirebaseAuthException;
}
