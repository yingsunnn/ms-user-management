package com.ying.msusermanagement.exception;

import lombok.Getter;
import lombok.Setter;

public class UserManagementException extends RuntimeException {

  @Getter
  @Setter
  private int httpStatus;

  public UserManagementException (String message) {
    super(message);
  }

  public UserManagementException(String message, Throwable cause) {
    super(message, cause);
  }
}
