package com.devictoralmeida.teste.controllers;

import com.devictoralmeida.teste.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

//  @PostMapping(value = "/login")
//  public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
//    return ResponseEntity.ok().body(authService.login(loginRequestDto));
//  }
}
