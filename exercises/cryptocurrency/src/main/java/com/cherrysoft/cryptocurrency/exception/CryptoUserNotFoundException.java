package com.cherrysoft.cryptocurrency.exception;

public class CryptoUserNotFoundException extends RuntimeException {

  public CryptoUserNotFoundException(String username) {
    super(String.format("User with username: %s not found!", username));
  }

}
