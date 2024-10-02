package com.devictoralmeida.teste.services.impl;

import com.devictoralmeida.teste.services.FileService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Qualifier("local")
public class FileServiceLocaImpl implements FileService {
  private final Logger logger = LoggerFactory.getLogger(FileServiceLocaImpl.class);

  @Value("${STORAGE_PATH}")
  private String storagePath;

  @Override
  public void upload(MultipartFile arquivo) throws IOException {
    try {
      logger.info("Iniciando upload do arquivo");
//      byte[] bytes = arquivo.getBytes();
      File uploadedFile = new File(storagePath + arquivo.getOriginalFilename());
      arquivo.transferTo(uploadedFile);
      logger.info("Upload concluído!");
    } catch (IOException e) {
      logger.error("Erro ao fazer upload do arquivo " + arquivo.getOriginalFilename() + "Mensagem erro: " + e.getMessage());
    }
  }

}