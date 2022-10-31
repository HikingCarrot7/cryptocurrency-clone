package com.cherrysoft.cryptocurrency.exception;

public class ApplicationException extends RuntimeException {

  public ApplicationException(final Throwable e) {
    super(e);
  }

}
