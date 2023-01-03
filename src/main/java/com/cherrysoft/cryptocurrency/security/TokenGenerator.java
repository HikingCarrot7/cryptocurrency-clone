package com.cherrysoft.cryptocurrency.security;

import com.cherrysoft.cryptocurrency.web.dtos.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class TokenGenerator {
  private final JwtEncoder encoder;

  public LoginResponseDTO issueToken(Authentication authentication) {
    SecurityCryptoUser user = (SecurityCryptoUser) authentication.getPrincipal();
    return new LoginResponseDTO(user.getUsername(), generateAccessToken(authentication));
  }

  private String generateAccessToken(Authentication authentication) {
    Instant now = Instant.now();
    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("manics")
        .issuedAt(now)
        .expiresAt(now.plus(6, ChronoUnit.HOURS))
        .subject(authentication.getName())
        .build();

    return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }

}
