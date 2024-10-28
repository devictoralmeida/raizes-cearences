package com.devictoralmeida.teste.config;

import com.devictoralmeida.teste.config.filters.FirebaseAuthenticationFilter;
import com.devictoralmeida.teste.shared.constants.RotasPermitidasConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
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
@Profile("prod")
public class SecurityConfigProd {
  @Autowired
  @Lazy
  private final FirebaseAuthenticationFilter filter;

  public SecurityConfigProd(@Lazy FirebaseAuthenticationFilter filter) {
    this.filter = filter;
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .requiresChannel(channel -> channel.anyRequest().requiresSecure())
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .invalidSessionUrl("/auth/login")
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(true)
                    .expiredUrl("/auth/login"))
            .authorizeHttpRequests(auth -> {
              auth.requestMatchers(HttpMethod.POST, "/usuario").permitAll()
                      .requestMatchers(RotasPermitidasConstants.ROTAS_PERMITIDAS).permitAll()
                      .anyRequest().authenticated();
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
