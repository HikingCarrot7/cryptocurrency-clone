package com.cherrysoft.cryptocurrency.security;

import com.cherrysoft.cryptocurrency.model.CryptoUser;
import com.cherrysoft.cryptocurrency.service.CryptoUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtToCryptoUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {
  private final CryptoUserService cryptoUserService;

  @Override
  public UsernamePasswordAuthenticationToken convert(@NonNull Jwt jwt) {
    String username = jwt.getSubject();
    CryptoUser user = cryptoUserService.getCryptoUserByUsername(username);
    SecurityCryptoUser securityUser = new SecurityCryptoUser(user);
    return new UsernamePasswordAuthenticationToken(securityUser, jwt, securityUser.getAuthorities());
  }

}
