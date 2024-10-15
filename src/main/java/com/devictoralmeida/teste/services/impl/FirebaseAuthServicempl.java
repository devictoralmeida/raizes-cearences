package com.devictoralmeida.teste.services.impl;

import com.devictoralmeida.teste.dto.request.UsuarioRequestDto;
import com.devictoralmeida.teste.services.FirebaseAuthService;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FirebaseAuthServicempl implements FirebaseAuthService {
  private final String collection = "usuarios";

  @Override
  public UserRecord criarUsuarioFirebase(UsuarioRequestDto dto) throws FirebaseAuthException {
    String nome = dto.getPessoaPerfil().getDadosPessoaFisica() != null
            ? dto.getPessoaPerfil().getDadosPessoaFisica().getNome()
            : dto.getPessoaPerfil().getDadosPessoaJuridica().getRazaoSocial();
    UserRecord.CreateRequest request = createFirebaseUserRequest(dto, nome);
    UserRecord usuarioFirebase = FirebaseAuth.getInstance().createUser(request);

    Firestore db = FirestoreClient.getFirestore();
    DocumentReference userRef = db.collection(collection).document(usuarioFirebase.getUid());
    Map<String, Object> userData = createFirebaseUserData(dto, usuarioFirebase);

    userRef.set(userData);
    return usuarioFirebase;
  }

  private UserRecord.CreateRequest createFirebaseUserRequest(UsuarioRequestDto dto, String nome) {
    UserRecord.CreateRequest request = new UserRecord.CreateRequest()
            .setEmailVerified(false)
            .setPassword(dto.getSenha())
            .setDisplayName(nome)
            .setDisabled(false);

    if (dto.getPessoaPerfil().getContato().getNumeroContato() != null) {
      request.setPhoneNumber("+55" + dto.getPessoaPerfil().getContato().getNumeroContato());
    }

    if (dto.getPessoaPerfil().getContato().getEmail() != null) {
      request.setEmail(dto.getPessoaPerfil().getContato().getEmail());
    }

    return request;
  }

  private Map<String, Object> createFirebaseUserData(UsuarioRequestDto dto, UserRecord usuarioFirebase) {
    Map<String, Object> userData = new HashMap<>();
    userData.put("uid", usuarioFirebase.getUid());
    userData.put("nome", usuarioFirebase.getDisplayName());
    userData.put("tipo_perfil", dto.getTipoPerfil().name());
    userData.put("email_verificado", usuarioFirebase.isEmailVerified());

    if (dto.getPessoaPerfil().getContato().getNumeroContato() != null) {
      userData.put("telefone", "+55" + dto.getPessoaPerfil().getContato().getNumeroContato());
    }

    if (dto.getPessoaPerfil().getContato().getEmail() != null) {
      userData.put("email", dto.getPessoaPerfil().getContato().getEmail());
    }

    return userData;
  }

  @Override
  public void emailVerificado(String uid) throws FirebaseAuthException {
    UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(uid).setEmailVerified(true);
    FirebaseAuth.getInstance().updateUser(request);
  }
}