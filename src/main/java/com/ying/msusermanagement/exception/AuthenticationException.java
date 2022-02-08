package com.ying.msusermanagement.exception;

public class AuthenticationException extends UserManagementException {

  public AuthenticationException(String message) {
    super(message);
  }

  public AuthenticationException(
      String message,
      Throwable cause
  ) {
    super(message, cause);
  }
}
