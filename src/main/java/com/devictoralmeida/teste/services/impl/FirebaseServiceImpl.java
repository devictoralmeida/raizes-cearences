package com.devictoralmeida.teste.services.impl;

import com.devictoralmeida.teste.dto.request.ContatoRequestDto;
import com.devictoralmeida.teste.dto.request.UsuarioRequestDto;
import com.devictoralmeida.teste.dto.request.update.ContatoUpdateRequestDto;
import com.devictoralmeida.teste.services.FirebaseService;
import com.devictoralmeida.teste.shared.constants.SharedConstants;
import com.devictoralmeida.teste.shared.constants.errors.AuthErrorsMessageConstants;
import com.devictoralmeida.teste.shared.constants.errors.FirebaseErrorsMessageConstants;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import com.devictoralmeida.teste.shared.exceptions.RecursoNaoEncontradoException;
import com.devictoralmeida.teste.shared.exceptions.SemAutorizacaoException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FirebaseServiceImpl implements FirebaseService {
  private final FirebaseAuth firebaseAuth;

  @Override
  public UserRecord criarUsuarioFirebase(UsuarioRequestDto dto) throws FirebaseAuthException {
    String nome = dto.getPessoaPerfil().getDadosPessoaFisica() != null
            ? dto.getPessoaPerfil().getDadosPessoaFisica().getNome() + " " + dto.getPessoaPerfil().getDadosPessoaFisica().getSobrenome()
            : dto.getPessoaPerfil().getDadosPessoaJuridica().getRazaoSocial();


    UserRecord.CreateRequest request = createFirebaseUserRequest(dto, nome);
    return firebaseAuth.createUser(request);
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
      throw new RecursoNaoEncontradoException(FirebaseErrorsMessageConstants.ERRO_USUARIO_NAO_ENCONTRADO);
    }
  }

  @Override
  public void atualizarContatoUsuarioFirebase(String uid, ContatoUpdateRequestDto requestDto, boolean usuarioPossuiEmail) throws FirebaseAuthException {
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
  }

  @Override
  public FirebaseToken verificarToken(String idToken) {
    try {
      return firebaseAuth.verifyIdToken(idToken, true);
    } catch (FirebaseAuthException e) {
      throw new SemAutorizacaoException(AuthErrorsMessageConstants.ERRO_AUTENTICACAO_TOKEN);
    }
  }

  @Override
  public void atualizarSenhaUsuarioFirebase(String firebaseUID, String senha) throws FirebaseAuthException {
    UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(firebaseUID).setPassword(senha);
    firebaseAuth.updateUser(request);
  }

  @Override
  public void adicionarPermissoesTokenFirebase(String uid, Collection<? extends GrantedAuthority> permissoes) {
    try {
      HashMap<String, Object> claims = new HashMap<>();
      List<String> permissoesList = permissoes.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
      claims.put(SharedConstants.PERMISSOES, permissoesList);
      firebaseAuth.setCustomUserClaims(uid, claims);
    } catch (FirebaseAuthException e) {
      throw new NegocioException(FirebaseErrorsMessageConstants.ERRO_ADICIONAR_PERMISSOES_TOKEN);
    }
  }
}