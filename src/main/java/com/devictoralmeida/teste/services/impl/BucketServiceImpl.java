package com.devictoralmeida.teste.services.impl;

import com.devictoralmeida.teste.dto.request.AnexoRequestDto;
import com.devictoralmeida.teste.entities.Anexo;
import com.devictoralmeida.teste.services.BucketService;
import com.devictoralmeida.teste.shared.exceptions.NegocioException;
import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BucketServiceImpl implements BucketService {
  private final MinioClient minioClient;

  @Value("${minio.bucket.name}")
  private String bucketName;

  @Autowired
  BucketServiceImpl(
          @Value("${minio.endpoint}") String endpoint,
          @Value("${minio.access.key}") String accessKey,
          @Value("${minio.secret.key}") String secretKey) {
    minioClient = MinioClient.builder()
            .endpoint(endpoint)
            .credentials(accessKey, secretKey)
            .build();
  }

  @Override
  public void upload(AnexoRequestDto dto, UUID anexoId) {
    MultipartFile arquivo = dto.getAnexo();
    String nomeArquivo = anexoId + dto.getNome();
    String contentType = arquivo.getContentType();

    try {
      InputStream inputStream = arquivo.getInputStream();
      minioClient.putObject(
              PutObjectArgs.builder()
                      .bucket(bucketName)
                      .object(anexoId.toString() + nomeArquivo)
                      .stream(inputStream, inputStream.available(), -1)
                      .contentType(contentType)
                      .build());

    } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
      throw new NegocioException("Erro ao fazer upload do arquivo " + arquivo.getOriginalFilename() + "Mensagem erro: " + e.getMessage());
    }
  }

  @Override
  public void deletarArquivos(List<Anexo> anexos) {
    List<DeleteObject> deleteObjects = anexos.stream()
            .map(anexo -> new DeleteObject(anexo.getId().toString() + anexo.getNome()))
            .toList();
    List<DeleteObject> objects = new LinkedList<>(deleteObjects);
    try {
      Iterable<Result<DeleteError>> results =
              minioClient.removeObjects(
                      RemoveObjectsArgs.builder()
                              .bucket(bucketName).
                              objects(objects)
                              .bypassGovernanceMode(true)
                              .build());
      for (Result<DeleteError> result : results) {
        DeleteError error = result.get();
        System.out.println(
                "Error in deleting object " + error.objectName() + "; " + error.message());
      }
    } catch (Exception e) {
      throw new NegocioException("Erro ao excluir arquivo do bucket mensagem erro: " + e.getMessage());
    }

  }

  @Override
  public void deletarArquivo(Anexo anexo) {
    try {
      minioClient.removeObject(
              RemoveObjectArgs.builder()
                      .bucket(bucketName)
                      .object(anexo.getId().toString() + anexo.getNome())
                      .bypassGovernanceMode(true)
                      .build());
    } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
      throw new NegocioException("Erro ao excluir arquivo do bucket: " + anexo.getNome() + " Mensagem erro: " + e.getMessage());
    }
  }
}