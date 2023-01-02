package com.cherrysoft.cryptocurrency.security;

import com.nimbusds.jose.jwk.RSAKey;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Jwks {

  public static RSAKey generateRsa() {
    KeyPair keyPair = KeyGeneratorUtils.generateRsaKeyPair();
    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
    return new RSAKey.Builder(publicKey)
        .privateKey(privateKey)
        .keyID(UUID.randomUUID().toString())
        .build();
  }

}
