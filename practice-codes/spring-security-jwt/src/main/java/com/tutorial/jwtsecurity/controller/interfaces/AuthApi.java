package com.tutorial.jwtsecurity.controller.interfaces;

import com.tutorial.jwtsecurity.domain.user.dto.MemberRequestDto;
import com.tutorial.jwtsecurity.domain.user.dto.TokenRequestDto;
import com.tutorial.jwtsecurity.common.response.ResponseErrorObject;
import com.tutorial.jwtsecurity.common.response.ResponseJsonObject;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
public interface AuthApi {

  @PostMapping(value = "/signup")
  @ApiOperation(
      value = "회원가입",
      notes = "/auth/signup",
      response = ResponseJsonObject.class,
      authorizations = @Authorization(value = "basic"))
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "signup Success", response = ResponseJsonObject.class),
      @ApiResponse(code = 400, message = "Bad Request", response = ResponseErrorObject.class),
      @ApiResponse(code = 401,message = "Unauthorized",response = ResponseErrorObject.class),
      @ApiResponse(code = 403,message = "Forbidden",response = ResponseErrorObject.class)
  })
  ResponseEntity<ResponseJsonObject> signup(
      @RequestBody(required = true) MemberRequestDto memberRequestDto
  );

  @PostMapping("/login")
  @ApiOperation(
      value = "로그인 (토큰검증)",
      notes = "/auth/login",
      response = ResponseJsonObject.class,
      authorizations = @Authorization(value = "basic"))
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "login Success", response = ResponseJsonObject.class),
      @ApiResponse(code = 400, message = "Bad Request", response = ResponseErrorObject.class),
      @ApiResponse(code = 401,message = "Unauthorized",response = ResponseErrorObject.class),
      @ApiResponse(code = 403,message = "Forbidden",response = ResponseErrorObject.class)
  })
  ResponseEntity<ResponseJsonObject> login(
      @RequestBody MemberRequestDto memberRequestDto
  );

  @PostMapping("/reissue")
  @ApiOperation(
      value = "토큰 재발급",
      notes = "/auth/reissue",
      response = ResponseJsonObject.class,
      authorizations = @Authorization(value = "bearer"))
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "reissue Success", response = ResponseJsonObject.class),
      @ApiResponse(code = 400, message = "Bad Request", response = ResponseErrorObject.class),
      @ApiResponse(code = 401,message = "Unauthorized",response = ResponseErrorObject.class),
      @ApiResponse(code = 403,message = "Forbidden",response = ResponseErrorObject.class)
  })
  ResponseEntity<ResponseJsonObject> reissue(
      @RequestBody TokenRequestDto tokenRequestDto
  );

}
