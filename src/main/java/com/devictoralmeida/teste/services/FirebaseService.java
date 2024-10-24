package com.devictoralmeida.teste.services;

import com.devictoralmeida.teste.dto.request.UsuarioRequestDto;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;

public interface FirebaseService {
  UserRecord criarUsuarioFirebase(UsuarioRequestDto dto);

  void emailVerificado(String uid);

  void deletarUsuarioFirebase(String uid);

  FirebaseToken verificarToken(String idToken) throws FirebaseAuthException;
}
