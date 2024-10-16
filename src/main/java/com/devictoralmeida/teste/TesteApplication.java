package com.devictoralmeida.teste;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@OpenAPIDefinition(
        info = @Info(title = "API", version = "1.0", description = "Documentação da API"),
        servers = {@io.swagger.v3.oas.annotations.servers.Server(url = "/", description = "API")}
)
@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.devictoralmeida", repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class TesteApplication {

  public static void main(String[] args) {
    SpringApplication.run(TesteApplication.class, args);
  }

}
