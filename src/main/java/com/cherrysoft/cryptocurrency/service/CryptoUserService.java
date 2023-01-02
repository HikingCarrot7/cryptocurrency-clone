package com.cherrysoft.cryptocurrency.service;

import com.cherrysoft.cryptocurrency.exception.CryptoUserNotFoundException;
import com.cherrysoft.cryptocurrency.exception.UsernameAlreadyTaken;
import com.cherrysoft.cryptocurrency.model.CryptoUser;
import com.cherrysoft.cryptocurrency.repository.CryptoUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CryptoUserService {
  private final PasswordEncoder passwordEncoder;
  private final CryptoUserRepository cryptoUserRepository;

  public CryptoUser getCryptoUserByUsername(String username) {
    return cryptoUserRepository
        .getCryptoUserByUsername(username)
        .orElseThrow(() -> new CryptoUserNotFoundException(username));
  }

  public CryptoUser createCryptoUser(CryptoUser cryptoUser) {
    ensureUniqueUsername(cryptoUser.getUsername());
    String encodedPassword = passwordEncoder.encode(cryptoUser.getPassword());
    cryptoUser.setPassword(encodedPassword);
    return cryptoUserRepository.save(cryptoUser);
  }

  private void ensureUniqueUsername(String username) {
    if (cryptoUserRepository.existsByUsername(username)) {
      throw new UsernameAlreadyTaken(username);
    }
  }

}
