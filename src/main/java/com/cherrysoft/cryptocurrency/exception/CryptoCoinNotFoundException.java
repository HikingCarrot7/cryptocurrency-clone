package com.cherrysoft.cryptocurrency.exception;

public class CryptoCoinNotFoundException extends RuntimeException {

  public CryptoCoinNotFoundException(String id) {
    super(String.format("Crypto coin with ID: %s doesn't exist", id));
  }

}
