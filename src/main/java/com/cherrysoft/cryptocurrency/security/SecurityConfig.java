package com.cherrysoft.cryptocurrency.security;

import com.cherrysoft.cryptocurrency.security.service.CryptoUserDetailsService;
import com.cherrysoft.cryptocurrency.security.support.KeyPairProvider;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final PasswordEncoder passwordEncoder;
  private final JwtToCryptoUserConverter jwtToUserConverter;
  private final KeyPairProvider keyPairProvider;
  private static final String[] AUTH_WHITELIST = {
      "/login",
      "/register",
      "/swagger-ui/**",
      "/swagger-resources/**",
      "/v3/api-docs/**"
  };

  @Bean
  DaoAuthenticationProvider authManager(CryptoUserDetailsService cryptoUserDetailsService) {
    var authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(cryptoUserDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder);
    return authProvider;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeRequests(auth -> auth
            .antMatchers(AUTH_WHITELIST).permitAll()
            .anyRequest().authenticated()
        )
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .oauth2ResourceServer(oauth2 ->
            oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtToUserConverter))
        )
        .exceptionHandling(exceptions -> exceptions
            .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
            .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
        )
        .build();
  }

  @Bean
  JwtEncoder jwtEncoder() {
    JWK jwk = new RSAKey
        .Builder(keyPairProvider.getAccessTokenPublicKey())
        .privateKey(keyPairProvider.getAccessTokenPrivateKey())
        .build();
    return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(jwk)));
  }

  @Bean
  JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder
        .withPublicKey(keyPairProvider.getAccessTokenPublicKey())
        .build();
  }

}
