package com.devictoralmeida.teste.config;

import com.devictoralmeida.teste.config.filters.FirebaseAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Autowired
  @Lazy
  private final FirebaseAuthenticationFilter filter;

  public SecurityConfig(@Lazy FirebaseAuthenticationFilter filter) {
    this.filter = filter;
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> {
              auth.requestMatchers("/home").authenticated();
              auth.anyRequest().permitAll();
            })
//            .formLogin((withDefaults()));
//            .addFilterAfter(new FirebaseAuthenticationFilter(authService), BasicAuthenticationFilter.class);
            .addFilterAfter(filter, BasicAuthenticationFilter.class);
    return http.build();
  }

//  @Bean
//  AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//    return authenticationConfiguration.getAuthenticationManager();
//  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
