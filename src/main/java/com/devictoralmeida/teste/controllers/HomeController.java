package com.devictoralmeida.teste.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  @GetMapping(value = "/home")
  public void test(Authentication authentication) {
    if (authentication != null) {
      System.out.println(authentication);
      System.out.println(authentication.getName());
      System.out.println(authentication.getClass());
      System.out.println(authentication.getCredentials());
      System.out.println(authentication.getPrincipal());
      System.out.println(authentication.isAuthenticated());
      System.out.println(authentication.getAuthorities());
    }

  }
}
