package com.devictoralmeida.teste.config;

import io.minio.MinioClient;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class MinioConfig {
  private static final Logger log = LoggerFactory.getLogger(MinioConfig.class);

  @Value("${minio.endpoint}")
  private String endpoint;

  @Value("${minio.access-key}")
  private String accessKey;

  @Value("${minio.secret-key}")
  private String secretKey;

  @Value("${minio.bucket.name}")
  private String bucketName;

  @Bean
  public MinioClient minioClient() {
    return MinioClient.builder()
            .endpoint("http://localhost:9000")
            .credentials("victor", "12345678")
            .build();
  }
}