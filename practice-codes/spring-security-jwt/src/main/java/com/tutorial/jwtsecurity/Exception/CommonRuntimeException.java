package com.tutorial.jwtsecurity.Exception;

import lombok.Getter;

@Getter
public class CommonRuntimeException extends RuntimeException {

  private ErrorCode errorCode;

  public CommonRuntimeException(String message,ErrorCode errorCode){
    super(message);
    this.errorCode = errorCode;
  }
}
