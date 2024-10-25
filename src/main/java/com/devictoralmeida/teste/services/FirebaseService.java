package com.devictoralmeida.teste.services;

import com.devictoralmeida.teste.dto.request.ContatoUpdateRequestDto;
import com.devictoralmeida.teste.dto.request.UsuarioRequestDto;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;

public interface FirebaseService {
  UserRecord criarUsuarioFirebase(UsuarioRequestDto dto);

  UserRecord findUserByUid(String uid);

  void emailVerificado(String uid);

  void deletarUsuarioFirebase(String uid);

  void atualizarContatoUsuarioFirebase(String uid, ContatoUpdateRequestDto requestDto, boolean usuarioPossuiEmail);

  FirebaseToken verificarToken(String idToken);
}
