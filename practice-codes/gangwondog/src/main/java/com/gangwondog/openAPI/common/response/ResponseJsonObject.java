package com.gangwondog.openAPI.common.response;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseJsonObject<T> {

  @ApiModelProperty(notes = "리턴 코드", example = "200")
  final private Integer code = HttpStatus.OK.value();

  @ApiModelProperty(notes = "리턴 HTTP 상태", example = "OK")
  final private HttpStatus httpStatus = HttpStatus.OK;

  @ApiModelProperty(notes = "리턴 메시지", example = "완료되었습니다.")
  private String message;

  @ApiModelProperty(notes = "리턴데이터", example = "Object")
  private T data;


}
