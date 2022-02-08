package com.ying.msusermanagement.exception;

public class DataNotExistException extends UserManagementException {

  public DataNotExistException (String message) {
    super(message);
  }

  public DataNotExistException(
      String message,
      Throwable cause
  ) {
    super(message, cause);
  }
}
