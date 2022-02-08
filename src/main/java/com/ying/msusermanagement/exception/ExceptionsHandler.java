package com.ying.msusermanagement.exception;

import com.ying.msusermanagement.dto.ErrorResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
@Slf4j
public class ExceptionsHandler {

  @ExceptionHandler(DuplicatedDataException.class)
  public ErrorResponse handleDuplicatedDataException(
      HttpServletRequest request,
      HttpServletResponse response,
      DuplicatedDataException e
  ) {
    return this.handleException(HttpStatus.BAD_REQUEST.value(), request, response, e);
  }

  @ExceptionHandler(DataNotExistException.class)
  public ErrorResponse handleDataNotExistException(
      HttpServletRequest request,
      HttpServletResponse response,
      DataNotExistException e
  ) {
    return this.handleException(HttpStatus.BAD_REQUEST.value(), request, response, e);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ErrorResponse handleAuthenticationException (
      HttpServletRequest request,
      HttpServletResponse response,
      DataNotExistException e
  ) {
    return this.handleException(HttpStatus.UNAUTHORIZED.value(), request, response, e);
  }


  /**
   * Root exception
   * @param request
   * @param response
   * @param e
   * @return
   */
  @ExceptionHandler(UserManagementException.class)
  public ErrorResponse handleUserManagementException (
      HttpServletRequest request,
      HttpServletResponse response,
      DataNotExistException e
  ) {
    return this.handleException(e.getHttpStatus(), request, response, e);
  }

  /**
   * Default exception
   * @param request
   * @param response
   * @param e
   * @return
   */
  @ExceptionHandler(Exception.class)
  public ErrorResponse handleDefaultException(
      HttpServletRequest request,
      HttpServletResponse response,
      Exception e
  ) {
    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    log.error(e.getMessage(), e);

    return ErrorResponse.builder()
        .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .errorMessage(e.getMessage())
        .build();
  }

  private ErrorResponse handleException(
      Integer httpStatus,
      HttpServletRequest request,
      HttpServletResponse response,
      UserManagementException e
  ) {
    response.setStatus(httpStatus);
    log.error(e.getMessage(), e);

    return ErrorResponse.builder()
        .httpStatus(httpStatus)
        .errorMessage(e.getMessage())
        .build();
  }
}
