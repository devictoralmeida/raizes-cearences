package com.devictoralmeida.teste.services;

import com.devictoralmeida.teste.dto.request.UsuarioRequestDto;
import com.devictoralmeida.teste.dto.request.update.ContatoUpdateRequestDto;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface FirebaseService {
  UserRecord criarUsuarioFirebase(UsuarioRequestDto dto);

  UserRecord findUserByUid(String uid);

  void emailVerificado(String uid);

  void deletarUsuarioFirebase(String uid);

  void atualizarContatoUsuarioFirebase(String uid, ContatoUpdateRequestDto requestDto, boolean usuarioPossuiEmail);

  FirebaseToken verificarToken(String idToken);

  void atualizarSenhaUsuarioFirebase(String firebaseUID, String senha);

  void adicionarPermissoesTokenFirebase(String uid, Collection<? extends GrantedAuthority> permissoes);
}
