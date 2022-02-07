package com.ying.msusermanagement.exception;

public class DuplicatedDataException extends UserManagementException {

  public DuplicatedDataException (String message) {
    super(message);
  }

  public DuplicatedDataException(String message, Throwable cause) {
    super(message, cause);
  }
}
