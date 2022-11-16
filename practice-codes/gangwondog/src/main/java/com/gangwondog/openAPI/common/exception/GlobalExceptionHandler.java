package com.gangwondog.openAPI.common.exception;

import com.gangwondog.openAPI.common.response.ResponseErrorObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(NotFoundUsernameException.class)
  public ResponseEntity<ResponseErrorObject> handleUsernameNotFoundException(NotFoundUsernameException exception){
    log.error("handleUsernameNotFoundException", exception);
    return ResponseErrorObject.toErrorResponse(exception.getErrorCode());
  }

  @ExceptionHandler(CommonRuntimeException.class)
  public ResponseEntity<ResponseErrorObject> handleCommonRuntimeException(CommonRuntimeException exception){
    log.error("CommonRuntimeException",exception);
    return ResponseErrorObject.toErrorResponse(exception.getErrorCode());
  }
}
