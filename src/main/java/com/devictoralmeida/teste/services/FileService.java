package com.devictoralmeida.teste.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
  void upload(MultipartFile arquivo);
}
