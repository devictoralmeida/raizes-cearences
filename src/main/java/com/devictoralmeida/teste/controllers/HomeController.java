package com.devictoralmeida.teste.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  @GetMapping(value = "/login")
  public String test() {
    return "Hello World!";
  }
}
