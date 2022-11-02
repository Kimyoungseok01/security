package com.tutorial.jwtsecurity.controller;


import com.tutorial.jwtsecurity.domain.user.dto.MemberRequestDto;
import com.tutorial.jwtsecurity.domain.user.dto.TokenRequestDto;
import com.tutorial.jwtsecurity.controller.interfaces.AuthApi;
import com.tutorial.jwtsecurity.common.response.ResponseJsonObject;
import com.tutorial.jwtsecurity.domain.user.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController implements AuthApi {
    private final AuthService authService;

    private ResponseJsonObject response;

    public ResponseEntity<ResponseJsonObject> signup(MemberRequestDto memberRequestDto) {
        response = ResponseJsonObject.builder()
            .code(HttpStatus.OK.value())
            .httpStatus(HttpStatus.OK)
            .message("회원가입을 진심으로 축하합니다.")
            .data(authService.signup(memberRequestDto))
            .build();
        return ResponseEntity.ok()
            .body(response);
    }

    public ResponseEntity<ResponseJsonObject> login(MemberRequestDto memberRequestDto) {
        response = ResponseJsonObject.builder()
            .code(HttpStatus.OK.value())
            .httpStatus(HttpStatus.OK)
            .message("로그인 되었습니다.")
            .data(authService.login(memberRequestDto))
            .build();
        return ResponseEntity.ok()
            .body(response);
    }

    public ResponseEntity<ResponseJsonObject> reissue(TokenRequestDto tokenRequestDto) {
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
