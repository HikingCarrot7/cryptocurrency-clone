package com.cherrysoft.cryptocurrency.exception;

public class BadCredentialsException extends RuntimeException {

  public BadCredentialsException() {
    super("Incorrect login or password entered. Try again");
  }

}
