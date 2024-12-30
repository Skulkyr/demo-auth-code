package org.pogonin.authservice.core.exception;

public class ConfirmCodeNotFoundOrExpiredException extends RuntimeException {
  public ConfirmCodeNotFoundOrExpiredException(String message) {
    super(message);
  }
}
