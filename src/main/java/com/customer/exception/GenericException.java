package com.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class GenericException extends RuntimeException {
  public GenericException() {
    super();
  }

  public GenericException(String message, Throwable cause) {
    super(message, cause);
  }

  public GenericException(String message) {
    super(message);
  }

  public GenericException(Throwable cause) {
    super(cause);
  }
}
