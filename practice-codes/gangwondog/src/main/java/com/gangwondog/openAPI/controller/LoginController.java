package com.gangwondog.openAPI.controller;


import com.gangwondog.openAPI.controller.interfaces.LoginApi;
import com.gangwondog.openAPI.domain.login.LoginService;
import com.gangwondog.openAPI.domain.login.dto.MemberRequestDto;
import com.gangwondog.openAPI.domain.login.dto.TokenRequestDto;
import com.gangwondog.openAPI.common.response.ResponseJsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController implements LoginApi {
    private final LoginService loginService;

    //private ResponseJsonObject response;

    public ResponseEntity<ResponseJsonObject> signup(MemberRequestDto memberRequestDto) {
        ResponseJsonObject response = ResponseJsonObject.builder()
            //.code(HttpStatus.OK.value())
            //.httpStatus(HttpStatus.OK)
            .message("회원가입을 진심으로 축하합니다.")
            .data(loginService.signup(memberRequestDto))
            .build();

        return ResponseEntity.ok()
            .body(response);
    }

    public ResponseEntity<ResponseJsonObject> login(MemberRequestDto memberRequestDto) {
        ResponseJsonObject response = ResponseJsonObject.builder()
            //.code(HttpStatus.OK.value())
            //.httpStatus(HttpStatus.OK)
            .message("로그인 되었습니다.")
            .data(loginService.login(memberRequestDto))
            .build();
        return ResponseEntity.ok()
            .body(response);
    }

    public ResponseEntity<ResponseJsonObject> reissue(TokenRequestDto tokenRequestDto) {
        ResponseJsonObject response = ResponseJsonObject.builder()
            //.code(HttpStatus.OK.value())
            //.httpStatus(HttpStatus.OK)
            .message("토큰 재발급이 완료 되었습니다.")
            .data(loginService.reissue(tokenRequestDto))
            .build();
        return ResponseEntity.ok()
            .body(response);
    }
}
