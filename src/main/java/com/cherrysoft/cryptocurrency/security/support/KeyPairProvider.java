package com.cherrysoft.cryptocurrency.security.support;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import static java.util.Objects.isNull;

@Component
public class KeyPairProvider {
  @Value("${access-token.private}")
  private String accessTokenPrivateKeyPath;

  @Value("${access-token.public}")
  private String accessTokenPublicKeyPath;

  private KeyPair accessTokenKeyPair;

  private KeyPair getAccessTokenKeyPair() {
    if (isNull(accessTokenKeyPair)) {
      var keyPairProvider = new KeyPairRepository(accessTokenPublicKeyPath, accessTokenPrivateKeyPath);
      accessTokenKeyPair = keyPairProvider.getKeyPair();
    }
    return accessTokenKeyPair;
  }

  public RSAPublicKey getAccessTokenPublicKey() {
    return (RSAPublicKey) getAccessTokenKeyPair().getPublic();
  }

  public RSAPrivateKey getAccessTokenPrivateKey() {
    return (RSAPrivateKey) getAccessTokenKeyPair().getPrivate();
  }

}
