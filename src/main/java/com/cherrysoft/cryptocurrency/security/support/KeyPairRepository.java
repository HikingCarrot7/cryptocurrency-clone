package com.cherrysoft.cryptocurrency.security.support;

import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class KeyPairRepository {
  private final KeyPairFileDAO keyPairFileDao;

  public KeyPairRepository(String publicKeysPath, String privateKeysPath) {
    this.keyPairFileDao = new KeyPairFileDAO(publicKeysPath, privateKeysPath);
  }

  public KeyPair getKeyPair() {
    if (keyPairFileDao.bothKeyPairsFilesExists()) {
      return generateKeyPairsFromFiles();
    }
    return generateAndSaveNewKeyPairs();
  }

  private KeyPair generateKeyPairsFromFiles() {
    try {
      KeyFactory keyFactory = getRSAKeyFactory();
      EncodedKeySpec publicKeySpec = keyPairFileDao.readPublicKey();
      PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

      EncodedKeySpec privateKeySpec = keyPairFileDao.readPrivateKey();
      PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

      return new KeyPair(publicKey, privateKey);
    } catch (InvalidKeySpecException e) {
      throw new RuntimeException(e);
    }
  }

  private KeyPair generateAndSaveNewKeyPairs() {
    keyPairFileDao.generateKeyPairsDirectory();
    KeyPair newKeyPairs = generateNewKeyPairs();
    EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(newKeyPairs.getPublic().getEncoded());
    keyPairFileDao.savePublicKey(publicKeySpec);
    EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(newKeyPairs.getPrivate().getEncoded());
    keyPairFileDao.savePrivateKey(privateKeySpec);
    return newKeyPairs;
  }

  private KeyPair generateNewKeyPairs() {
    KeyPairGenerator keyPairGenerator = getRSAKeyPairGenerator();
    keyPairGenerator.initialize(2048);
    return keyPairGenerator.generateKeyPair();
  }

  private KeyFactory getRSAKeyFactory() {
    try {
      return KeyFactory.getInstance("RSA");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  private KeyPairGenerator getRSAKeyPairGenerator() {
    try {
      return KeyPairGenerator.getInstance("RSA");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

}
