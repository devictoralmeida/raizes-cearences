package com.devictoralmeida.teste.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Configuration
@RequiredArgsConstructor
public class FirebaseConfig {
  private static final Logger log = LoggerFactory.getLogger(FirebaseConfig.class);

  @PostConstruct
  public void initialize() throws IOException {
    try {
      InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("./raizes-cearences-firebase-adminsdk-9c1mf-c0364ca5df.json");
      FirebaseOptions options = new FirebaseOptions.Builder()
              .setCredentials(GoogleCredentials.fromStream(serviceAccount))
              .setDatabaseUrl("https://raizes-cearences-default-rtdb.firebaseio.com")
              .build();

      if (FirebaseApp.getApps().isEmpty()) {
        FirebaseApp.initializeApp(options);
      }

      log.info("Firebase inicializado");
    } catch (Exception e) {
      log.error("Erro de configuração do Firebase");
      throw e;
    }
  }

  @Bean
  public FirebaseAuth firebaseAuth() {
    return FirebaseAuth.getInstance();
  }
}
