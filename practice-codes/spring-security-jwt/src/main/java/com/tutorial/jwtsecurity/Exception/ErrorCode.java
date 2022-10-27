package com.tutorial.jwtsecurity.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  UNKNOWN_ERROR(1,HttpStatus.BAD_REQUEST,"알 수 없는 오류입니다"),
  EXIST_USER(2, HttpStatus.BAD_REQUEST,"이미 가입되어 있는 유저입니다."),
  UNAUTHORIZED(401,HttpStatus.UNAUTHORIZED,"사용자를 찾을 수 없습니다."),
  WRONG_TYPE_TOKEN(450,HttpStatus.UNAUTHORIZED,"잘못된 JWT 서명입니다."),
  EXPIRED_TOKEN(451,HttpStatus.UNAUTHORIZED,"만료된 JWT 토큰입니다."),
  UNSUPPORTED_TOKEN(452,HttpStatus.UNAUTHORIZED,"지원되지 않는 JWT 토큰입니다."),
  WRONG_TOKEN(453,HttpStatus.UNAUTHORIZED,"JWT 토큰이 잘못되었습니다."),
  NOTEQUAL_TOKEN(454,HttpStatus.UNAUTHORIZED,"토큰의 유저 정보가 일치하지 않습니다."),
  NEED_AUTHORITIES(455,HttpStatus.UNAUTHORIZED,"권한 정보가 없는 토큰 입니다."),
  WRONG_BASIC_TOKEN(456,HttpStatus.UNAUTHORIZED,"Basic 토큰 정보가 잘못 되었습니다.");



  private Integer code;

  private HttpStatus httpStatus;

  private String message;
}
