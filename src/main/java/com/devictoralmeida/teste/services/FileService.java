package com.devictoralmeida.teste.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
  void upload(MultipartFile arquivo) throws IOException;
}
