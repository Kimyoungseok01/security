package com.gangwondog.openAPI.common.exception;

import com.gangwondog.openAPI.common.error.ErrorCode;
import lombok.Getter;

@Getter
public class TempJwtException extends Exception {

  private ErrorCode errorCode;

  public TempJwtException(String message,ErrorCode errorCode){
    super(message);
    this.errorCode = errorCode;
  }
}
