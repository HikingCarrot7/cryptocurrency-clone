package com.cherrysoft.cryptocurrency.exception.handler;

import com.cherrysoft.cryptocurrency.exception.ApplicationException;
import com.cherrysoft.cryptocurrency.exception.BadCredentialsException;
import com.cherrysoft.cryptocurrency.exception.ErrorResponse;
import com.cherrysoft.cryptocurrency.exception.UsernameAlreadyTaken;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class ApplicationExceptionHandler {

  @ExceptionHandler(ApplicationException.class)
  public ResponseEntity<Object> appExceptionHandler(final ApplicationException e) {
    return throwCustomException(e, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<Object> appExceptionHandler(final BadCredentialsException e) {
    return throwCustomException(e, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UsernameAlreadyTaken.class)
  public ResponseEntity<Object> appExceptionHandler(final UsernameAlreadyTaken e) {
    return throwCustomException(e, HttpStatus.BAD_REQUEST);
  }

  private ResponseEntity<Object> throwCustomException(final RuntimeException e, final HttpStatus status) {
    return new ResponseEntity<>(new ErrorResponse(e, status), status);
  }

}
