package com.tutorial.jwtsecurity.common.exception;

import com.tutorial.jwtsecurity.common.error.ErrorCode;
import lombok.Getter;

@Getter
public class NotFoundUsernameException extends RuntimeException {

  private ErrorCode errorCode;

  public NotFoundUsernameException(String message,ErrorCode errorCode){
    super(message);
    this.errorCode = errorCode;
  }
}
