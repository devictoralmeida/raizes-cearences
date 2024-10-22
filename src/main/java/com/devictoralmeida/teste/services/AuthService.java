package com.devictoralmeida.teste.services;

import com.devictoralmeida.teste.dto.request.LoginRequestDto;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface AuthService {
  String login(LoginRequestDto request);

  List<GrantedAuthority> criarPermissoes(String uid);
}
