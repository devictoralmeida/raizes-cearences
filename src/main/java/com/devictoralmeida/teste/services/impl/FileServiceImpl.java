package com.devictoralmeida.teste.services.impl;

import com.devictoralmeida.teste.services.FileService;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
  private final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
  private final MinioClient minioClient;

  @Value("${minio.bucket.name}")
  private String bucketName;

  @Override
  public void upload(MultipartFile arquivo) {
    try {
      logger.info("Iniciando upload do arquivo");
      String fileName = arquivo.getOriginalFilename();
      String newFileName = System.currentTimeMillis() + '-' + fileName;
      String contentType = arquivo.getContentType();

      InputStream inputStream = arquivo.getInputStream();
      minioClient.putObject(
              PutObjectArgs.builder()
                      .bucket(bucketName)
                      .object(newFileName)
                      .contentType(contentType)
                      .stream(inputStream, inputStream.available(), -1)
                      .build()
      );

      logger.info("Upload concluído!");
    } catch (IOException | ErrorResponseException | InsufficientDataException | InternalException |
             InvalidKeyException | InvalidResponseException | NoSuchAlgorithmException | ServerException |
             XmlParserException e) {
      logger.error("Erro ao fazer upload do arquivo " + arquivo.getOriginalFilename() + " Mensagem erro: " + e.getMessage());
      throw new NegocioException("Erro ao fazer upload do arquivo " + arquivo.getOriginalFilename() + "Mensagem erro: " + e.getMessage());
    }
  }
}