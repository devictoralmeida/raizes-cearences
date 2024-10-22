package com.devictoralmeida.teste.config;

import com.google.firebase.auth.FirebaseToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.util.Collection;
import java.util.List;


public class FirebaseAuthenticationToken extends AbstractAuthenticationToken {
  @Serial
  private static final long serialVersionUID = -3284631481003937789L;

  private FirebaseToken firebaseToken;
  private String idToken;

  public FirebaseAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
  }

  public FirebaseAuthenticationToken(String idToken, FirebaseToken firebaseToken, List<GrantedAuthority> authorities) {
    super(authorities);
    this.firebaseToken = firebaseToken;
    this.idToken = idToken;

  }

  @Override
  public Object getCredentials() {
    return idToken;
  }

  @Override
  public Object getPrincipal() {
    return firebaseToken.getUid();
  }
}
