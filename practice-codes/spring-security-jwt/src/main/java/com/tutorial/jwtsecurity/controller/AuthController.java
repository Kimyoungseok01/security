package com.tutorial.jwtsecurity.controller;


import com.tutorial.jwtsecurity.controller.dto.MemberRequestDto;
import com.tutorial.jwtsecurity.controller.dto.TokenRequestDto;
import com.tutorial.jwtsecurity.controller.dto.TokenDto;
import com.tutorial.jwtsecurity.entity.ResponseJsonObject;
import com.tutorial.jwtsecurity.service.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    private ResponseJsonObject response;

    @ApiOperation(value = "회원가입", notes = "회원가입", response = ResponseJsonObject.class)
    @PostMapping("/signup")
    public ResponseEntity<ResponseJsonObject> signup(@RequestBody MemberRequestDto memberRequestDto) {
        response = ResponseJsonObject.builder()
            .code(HttpStatus.OK.value())
            .httpStatus(HttpStatus.OK)
            .message("회원가입을 진심으로 축하합니다.")
            .data(authService.signup(memberRequestDto))
            .build();
        return ResponseEntity.ok()
            .body(response);
    }

    @ApiOperation(value = "로그인", notes = "토큰 검증후 로그인", response = ResponseJsonObject.class)
    @PostMapping("/login")
    public ResponseEntity<ResponseJsonObject> login(@RequestBody MemberRequestDto memberRequestDto) {
        response = ResponseJsonObject.builder()
            .code(HttpStatus.OK.value())
            .httpStatus(HttpStatus.OK)
            .message("로그인 되었습니다.")
            .data(authService.login(memberRequestDto))
            .build();
        return ResponseEntity.ok()
            .body(response);
    }

    @ApiOperation(value = "토큰 재발급", notes = "리프레시 토큰으로 어세스 토큰 발급", response = ResponseJsonObject.class)
    @PostMapping("/reissue")
    public ResponseEntity<ResponseJsonObject> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        response = ResponseJsonObject.builder()
            .code(HttpStatus.OK.value())
            .httpStatus(HttpStatus.OK)
            .message("토큰 재발급이 완료 되었습니다.")
            .data(authService.reissue(tokenRequestDto))
            .build();
        return ResponseEntity.ok()
            .body(response);
    }
}
