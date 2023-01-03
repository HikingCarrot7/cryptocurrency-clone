package com.cherrysoft.cryptocurrency.security.support;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class KeyPairFileDAO {
  public static final String TOKEN_KEYS_DIRECTORY = "access-refresh-token-keys";
  private final File publicKeysFile;
  private final File privateKeysFile;

  public KeyPairFileDAO(String publicKeysPath, String privateKeysPath) {
    this.publicKeysFile = new File(publicKeysPath);
    this.privateKeysFile = new File(privateKeysPath);
  }

  public boolean generateKeyPairsDirectory() {
    File directory = new File(TOKEN_KEYS_DIRECTORY);
    if (!directory.exists()) {
      return directory.mkdirs();
    }
    return false;
  }

  public EncodedKeySpec readPublicKey() {
    try {
      return tryReadPublicKey();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private EncodedKeySpec tryReadPublicKey() throws IOException {
    byte[] publicKeyBytes = Files.readAllBytes(publicKeysFile.toPath());
    return new X509EncodedKeySpec(publicKeyBytes);
  }

  public EncodedKeySpec readPrivateKey() {
    try {
      return tryReadPrivateKey();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private EncodedKeySpec tryReadPrivateKey() throws IOException {
    byte[] privateKeyBytes = Files.readAllBytes(privateKeysFile.toPath());
    return new PKCS8EncodedKeySpec(privateKeyBytes);
  }

  public void savePublicKey(EncodedKeySpec keySpec) {
    try {
      trySaveKey(keySpec, publicKeysFile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void savePrivateKey(EncodedKeySpec keySpec) {
    try {
      trySaveKey(keySpec, privateKeysFile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void trySaveKey(EncodedKeySpec keySpec, File keyFile) throws IOException {
    try (FileOutputStream fos = new FileOutputStream(keyFile)) {
      fos.write(keySpec.getEncoded());
    }
  }

  public boolean bothKeyPairsFilesExists() {
    return publicKeysFile.exists() && privateKeysFile.exists();
  }

}
