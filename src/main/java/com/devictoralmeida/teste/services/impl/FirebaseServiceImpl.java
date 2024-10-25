package com.devictoralmeida.teste.services.impl;

import com.devictoralmeida.teste.dto.request.ContatoRequestDto;
import com.devictoralmeida.teste.dto.request.ContatoUpdateRequestDto;
import com.devictoralmeida.teste.dto.request.UsuarioRequestDto;
import com.devictoralmeida.teste.services.FirebaseService;
import com.devictoralmeida.teste.shared.constants.SharedConstants;
import com.devictoralmeida.teste.shared.constants.errors.FirebaseErrorsMessageConstants;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import com.devictoralmeida.teste.shared.exceptions.RecursoNaoEncontradoException;
import com.devictoralmeida.teste.shared.exceptions.SemAutorizacaoException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FirebaseServiceImpl implements FirebaseService {
  private final FirebaseAuth firebaseAuth;

  @Override
  public UserRecord criarUsuarioFirebase(UsuarioRequestDto dto) {
    String nome = dto.getPessoaPerfil().getDadosPessoaFisica() != null
            ? dto.getPessoaPerfil().getDadosPessoaFisica().getNome() + " " + dto.getPessoaPerfil().getDadosPessoaFisica().getSobrenome()
            : dto.getPessoaPerfil().getDadosPessoaJuridica().getRazaoSocial();

    try {
      UserRecord.CreateRequest request = createFirebaseUserRequest(dto, nome);
      UserRecord firebaseUser = firebaseAuth.createUser(request);


//      Set<String> permissoes = new HashSet<>();
//      permissoes.add("AGRICULTOR");
//
//      HashMap<String, Object> claims = new HashMap<>();
//      claims.put(SharedConstants.PERMISSOES, permissoes);
//      String customToken = firebaseAuth.createCustomToken(firebaseUser.getUid());
//      firebaseAuth.setCustomUserClaims(firebaseUser.getUid(), claims);
      return firebaseUser;
    } catch (FirebaseAuthException e) {
      throw new NegocioException(FirebaseErrorsMessageConstants.ERRO_CRIAR_USUARIO);
    }
  }

  @Override
  public UserRecord findUserByUid(String uid) {
    try {
      return firebaseAuth.getUser(uid);
    } catch (FirebaseAuthException e) {
      throw new RecursoNaoEncontradoException(FirebaseErrorsMessageConstants.ERRO_USUARIO_NAO_ENCONTRADO);
    }
  }

  private UserRecord.CreateRequest createFirebaseUserRequest(UsuarioRequestDto dto, String nome) {
    UserRecord.CreateRequest request = new UserRecord.CreateRequest()
            .setEmailVerified(false)
            .setPassword(dto.getSenha())
            .setDisplayName(nome)
            .setDisabled(false);

    ContatoRequestDto contato = dto.getPessoaPerfil().getContato();

    if (contato.getNumeroWhatsapp() != null) {
      request.setPhoneNumber(SharedConstants.PREFIX_TELEFONE_BR + contato.getNumeroWhatsapp());
    }

    if (contato.getEmail() != null) {
      request.setEmail(contato.getEmail());
    } else {
      request.setEmail(contato.getNumeroWhatsapp() + SharedConstants.EMAIL_DOMINIO_RAIZES);
    }

    return request;
  }

  @Override
  public void emailVerificado(String uid) {
    try {
      UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(uid).setEmailVerified(true);
      firebaseAuth.updateUser(request);
    } catch (FirebaseAuthException e) {
      throw new NegocioException(FirebaseErrorsMessageConstants.ERRO_VERIFICACAO_EMAIL);
    }
  }

  @Override
  public void deletarUsuarioFirebase(String uid) {
    try {
      firebaseAuth.deleteUser(uid);
    } catch (FirebaseAuthException e) {
      throw new NegocioException(FirebaseErrorsMessageConstants.ERRO_DELETAR_USUARIO);
    }
  }

  @Override
  public void atualizarContatoUsuarioFirebase(String uid, ContatoUpdateRequestDto requestDto, boolean usuarioPossuiEmail) {
    try {
      UserRecord.UpdateRequest updateRequestFirebase = new UserRecord.UpdateRequest(uid);

      if (requestDto.getEmail() != null) {
        updateRequestFirebase.setEmail(requestDto.getEmail());
      }

      if (requestDto.getNumeroWhatsapp() != null) {
        updateRequestFirebase.setPhoneNumber(SharedConstants.PREFIX_TELEFONE_BR + requestDto.getNumeroWhatsapp());

        if (!usuarioPossuiEmail) {
          updateRequestFirebase.setEmail(requestDto.getNumeroWhatsapp() + SharedConstants.EMAIL_DOMINIO_RAIZES);
        }
      }

      firebaseAuth.updateUser(updateRequestFirebase);
    } catch (FirebaseAuthException e) {
      throw new NegocioException(FirebaseErrorsMessageConstants.ERRO_ATUALIZAR_CONTATO_USUARIO);
    }
  }

  @Override
  public FirebaseToken verificarToken(String idToken) {
    try {
      return firebaseAuth.verifyIdToken(idToken, true);
    } catch (FirebaseAuthException e) {
      throw new SemAutorizacaoException(FirebaseErrorsMessageConstants.ERRO_AUTENTICACAO_TOKEN);
    }
  }
}