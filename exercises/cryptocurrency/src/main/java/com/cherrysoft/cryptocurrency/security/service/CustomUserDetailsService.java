package com.cherrysoft.cryptocurrency.security.service;

import com.cherrysoft.cryptocurrency.exception.BadCredentialsException;
import com.cherrysoft.cryptocurrency.exception.CryptoUserNotFoundException;
import com.cherrysoft.cryptocurrency.model.CryptoUser;
import com.cherrysoft.cryptocurrency.service.CryptoUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  private final CryptoUserService cryptoUserService;

  @Override
  public UserDetails loadUserByUsername(String username) {
    try {
      CryptoUser cryptoUser = cryptoUserService.getCryptoUserByUsername(username);
      return User.builder()
          .username(cryptoUser.getUsername())
          .password(cryptoUser.getPassword())
          .authorities("READ")
          .build();
    } catch (CryptoUserNotFoundException e) {
      throw new BadCredentialsException();
    }
  }

}
