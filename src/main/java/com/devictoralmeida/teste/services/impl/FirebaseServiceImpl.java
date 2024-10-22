package com.devictoralmeida.teste.services.impl;

import com.devictoralmeida.teste.dto.request.UsuarioRequestDto;
import com.devictoralmeida.teste.services.FirebaseService;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FirebaseServiceImpl implements FirebaseService {
  @Override
  public UserRecord criarUsuarioFirebase(UsuarioRequestDto dto) {
    String nome = dto.getPessoaPerfil().getDadosPessoaFisica() != null
            ? dto.getPessoaPerfil().getDadosPessoaFisica().getNome() + " " + dto.getPessoaPerfil().getDadosPessoaFisica().getSobrenome()
            : dto.getPessoaPerfil().getDadosPessoaJuridica().getRazaoSocial();

    try {
      UserRecord.CreateRequest request = createFirebaseUserRequest(dto, nome);
      UserRecord firebaseUser = FirebaseAuth.getInstance().createUser(request);
//      Set<String> permissoes = new HashSet<>();
//      permissoes.add("AGRICULTOR");
//
//      HashMap<String, Object> claims = new HashMap<>();
//      claims.put(SharedConstants.PERMISSOES, permissoes);
//      FirebaseAuth.getInstance().setCustomUserClaims(firebaseUser.getUid(), claims);
      return firebaseUser;
    } catch (FirebaseAuthException e) {
      throw new NegocioException("Erro ao criar o usuário no Firebase");
    }
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

  @Override
  public void emailVerificado(String uid) {
    try {
      UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(uid).setEmailVerified(true);
      FirebaseAuth.getInstance().updateUser(request);
    } catch (FirebaseAuthException e) {
      throw new NegocioException("Erro ao verificar o e-mail do usuário no Firebase");
    }
  }

  @Override
  public void deletarUsuarioFirebase(String uid) {
    try {
      FirebaseAuth.getInstance().deleteUser(uid);
    } catch (FirebaseAuthException e) {
      throw new NegocioException("Erro ao deletar usuário no Firebase");
    }
  }
}