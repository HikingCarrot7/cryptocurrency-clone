package com.cherrysoft.cryptocurrency.exception.handler;

import com.cherrysoft.cryptocurrency.exception.ErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class HttpExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  @NonNull
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    final Map<String, String> validationErrors = new LinkedHashMap<>();
    ex.getBindingResult().getAllErrors().forEach(
        error -> validationErrors.put(((FieldError) error).getField(), error.getDefaultMessage())
    );
    return new ResponseEntity<>(new ErrorResponse(status, validationErrors), status);
  }

}
