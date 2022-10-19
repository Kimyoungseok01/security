package com.tutorial.jwtsecurity.Exception;

import java.io.IOException;
import lombok.Getter;

@Getter
public class NotFoundUsernameException extends RuntimeException {

  private ErrorCode errorCode;

  public NotFoundUsernameException(String message,ErrorCode errorCode){
    super(message);
    this.errorCode = errorCode;
  }
}
