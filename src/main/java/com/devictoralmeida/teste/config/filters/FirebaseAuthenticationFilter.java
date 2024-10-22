package com.devictoralmeida.teste.config.filters;

import com.devictoralmeida.teste.config.FirebaseAuthenticationToken;
import com.devictoralmeida.teste.services.AuthService;
import com.devictoralmeida.teste.shared.constants.SharedConstants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class FirebaseAuthenticationFilter extends OncePerRequestFilter {
  private final AuthService authService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String idToken = recoverToken(request, response);

    if (idToken == null) {
      return;
    }

    try {
      FirebaseToken token = FirebaseAuth.getInstance().verifyIdToken(idToken);
      List<GrantedAuthority> authorities = getAuthoritiesFromToken(token);
      SecurityContextHolder.getContext().setAuthentication(new FirebaseAuthenticationToken(idToken, token, authorities));
      SecurityContextHolder.getContext().getAuthentication().setAuthenticated(true);
    } catch (Exception e) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
      return;
    }

    filterChain.doFilter(request, response);
  }

  private String recoverToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String authHeader = request.getHeader("Authorization");

    if (authHeader == null || authHeader.isEmpty()) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token não informado");
      return null;
    }

    return authHeader.replace("Bearer ", "");
  }

  private List<GrantedAuthority> getAuthoritiesFromToken(FirebaseToken token) {
    List<String> permissoes = (List<String>) token.getClaims().getOrDefault(SharedConstants.PERMISSOES, List.of());
    Set<String> novasPermissoes = new HashSet<>(permissoes);
    novasPermissoes.addAll(permissoes);
    authService.criarPermissoes(token.getUid()).stream().map(GrantedAuthority::getAuthority).forEach(novasPermissoes::add);
    return AuthorityUtils.createAuthorityList(novasPermissoes);
  }
}
