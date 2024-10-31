package com.devictoralmeida.teste.controllers;

import com.devictoralmeida.teste.shared.constants.SharedConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HomeController {

  @GetMapping(value = "/home")
  public Map<String, Object> test(Authentication authentication) {
    HashMap<String, Object> claims = new HashMap<>();
    if (authentication != null) {
//      System.out.println(authentication);
//      System.out.println(authentication.getName());
//      System.out.println(authentication.getClass());
//      System.out.println(authentication.getCredentials());
//      System.out.println(authentication.isAuthenticated());
//      System.out.println(authentication.getAuthorities());
      List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
      claims.put(SharedConstants.PERMISSOES, roles);
    }
    return claims;
  }
}
