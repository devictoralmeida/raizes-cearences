package com.devictoralmeida.teste.config.filters;

import com.devictoralmeida.teste.entities.Usuario;
import com.devictoralmeida.teste.services.AuthService;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
  private final AuthService authService;

  public FirebaseAuthenticationFilter(@Lazy AuthService authService) {
    this.authService = authService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//    String requestURI = request.getRequestURI();
    String idToken = recoverToken(request);
//    boolean isRotaPermitida = Arrays.stream(RotasPermitidasConstants.ROTAS_PERMITIDAS)
//            .anyMatch(requestURI::matches);

//    if (idToken == null && !isRotaPermitida) {
//      throw new SemAutorizacaoException(AuthErrorsMessageConstants.ERRO_AUTENTICACAO_TOKEN);
//    }

//    if (request.getRequestURI().contains("/auth/login")) {
//
//    } else {

    if (idToken != null) {
      FirebaseToken token = authService.verificarToken(idToken);
      UserDetails user = authService.loadUserByUsername(token.getUid());
      Usuario usuario = (Usuario) user;
      authService.verificarUsuarioValido(usuario);
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
}
