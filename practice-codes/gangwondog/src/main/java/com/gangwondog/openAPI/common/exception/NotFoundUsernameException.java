package com.gangwondog.openAPI.common.exception;

import com.gangwondog.openAPI.common.error.ErrorCode;
import lombok.Getter;

@Getter
public class NotFoundUsernameException extends RuntimeException {

  private ErrorCode errorCode;

  public NotFoundUsernameException(String message,ErrorCode errorCode){
    super(message);
    this.errorCode = errorCode;
  }
}
