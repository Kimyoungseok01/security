package com.tutorial.jwtsecurity.common.response;

import com.tutorial.jwtsecurity.common.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseErrorObject {

  private Integer code;

  private HttpStatus httpStatus;

  private String message;

  public static ResponseEntity<ResponseErrorObject> toErrorResponse(ErrorCode errorCode){
    return ResponseEntity
        .status(errorCode.getHttpStatus())
        .body(ResponseErrorObject.builder()
            .code(errorCode.getCode())
            .httpStatus(errorCode.getHttpStatus())
            .message(errorCode.getMessage())
            .build());
  }

}
