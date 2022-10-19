package com.tutorial.jwtsecurity.Exception;

import io.jsonwebtoken.ClaimJwtException;
import lombok.Getter;

@Getter
public class TempJwtException extends Exception {

  private ErrorCode errorCode;

  public TempJwtException(String message,ErrorCode errorCode){
    super(message);
    this.errorCode = errorCode;
  }
}
