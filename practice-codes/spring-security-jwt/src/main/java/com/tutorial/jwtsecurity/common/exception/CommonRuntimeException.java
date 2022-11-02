package com.tutorial.jwtsecurity.common.exception;

import com.tutorial.jwtsecurity.common.error.ErrorCode;
import lombok.Getter;

@Getter
public class CommonRuntimeException extends RuntimeException {

  private ErrorCode errorCode;

  public CommonRuntimeException(String message,ErrorCode errorCode){
    super(message);
    this.errorCode = errorCode;
  }
}
