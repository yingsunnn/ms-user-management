package com.ying.msusermanagement.exception;

public class PermissionDeniedException extends UserManagementException {

  public PermissionDeniedException(String message) {
    super(message);
  }

  public PermissionDeniedException(
      String message,
      Throwable cause
  ) {
    super(message, cause);
  }
}
