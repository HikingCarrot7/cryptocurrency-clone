package com.cherrysoft.cryptocurrency.security.service;

import com.cherrysoft.cryptocurrency.controller.dtos.LoginResponseDTO;
import com.cherrysoft.cryptocurrency.model.CryptoUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static java.util.stream.Collectors.joining;

@Service
@RequiredArgsConstructor
public class LoginService {
  private final JwtEncoder encoder;
  private final AuthenticationManager authenticationManager;

  public LoginResponseDTO login(CryptoUser cryptoUser) {
    var authenticationToken = new UsernamePasswordAuthenticationToken(cryptoUser.getUsername(), cryptoUser.getPassword());
    Authentication authentication = authenticationManager.authenticate(authenticationToken);
    return new LoginResponseDTO(
        authentication.getPrincipal().toString(),
        generateToken(authentication)
    );
  }

  private String generateToken(Authentication authentication) {
    Instant now = Instant.now();
    String scope = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(joining(" "));
    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("self")
        .issuedAt(now)
        .expiresAt(now.plus(1, ChronoUnit.HOURS))
        .subject(authentication.getName())
        .claim("scope", scope)
        .build();
    return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }

}
