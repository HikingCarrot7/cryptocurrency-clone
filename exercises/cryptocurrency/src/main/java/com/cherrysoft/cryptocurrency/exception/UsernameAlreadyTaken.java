package com.cherrysoft.cryptocurrency.exception;

public class UsernameAlreadyTaken extends RuntimeException {

  public UsernameAlreadyTaken(String username) {
    super(String.format("Username %s is already taken", username));
  }

}
