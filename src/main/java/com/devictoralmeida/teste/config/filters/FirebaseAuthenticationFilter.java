package com.devictoralmeida.teste.config.filters;

import com.devictoralmeida.teste.services.UsuarioService;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FirebaseAuthenticationFilter extends OncePerRequestFilter {
  @Autowired
  @Lazy
  private final UsuarioService usuarioService;

  public FirebaseAuthenticationFilter(@Lazy UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  @SneakyThrows
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String idToken = recoverToken(request);

    if (idToken != null) {
      FirebaseToken token = usuarioService.verificarToken(idToken);
      UserDetails user = usuarioService.loadUserByUsername(token.getUid());
      Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);
  }

  private String recoverToken(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");

    if (authHeader == null || authHeader.isEmpty()) {
      return null;
    }

    return authHeader.replace("Bearer ", "");
  }

//  private List<GrantedAuthority> getAuthoritiesFromToken(FirebaseToken token) {
//    List<String> permissoes = (List<String>) token.getClaims().getOrDefault(SharedConstants.PERMISSOES, List.of());
//    Set<String> novasPermissoes = new HashSet<>(permissoes);
//    novasPermissoes.addAll(permissoes);
//    authService.criarPermissoes(token.getUid()).stream().map(GrantedAuthority::getAuthority).forEach(novasPermissoes::add);
//    return AuthorityUtils.createAuthorityList(novasPermissoes);
//  }
}
