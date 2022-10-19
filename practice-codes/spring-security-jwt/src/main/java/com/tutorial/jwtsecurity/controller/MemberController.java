package com.tutorial.jwtsecurity.controller;

import com.tutorial.jwtsecurity.controller.dto.MemberResponseDto;
import com.tutorial.jwtsecurity.entity.ResponseJsonObject;
import com.tutorial.jwtsecurity.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    private ResponseJsonObject response;

    @GetMapping("/me")
    public ResponseEntity<ResponseJsonObject> getMyMemberInfo() {
        //return ResponseEntity.ok(memberService.getMyInfo());
        response = ResponseJsonObject.builder()
            .code(HttpStatus.OK.value())
            .httpStatus(HttpStatus.OK)
            .message("현재 로그인 정보 조회 완료")
            .data(memberService.getMyInfo())
            .build();
        return new ResponseEntity<>(response,response.getHttpStatus());
    }

    @GetMapping("/email")
    public ResponseEntity<ResponseJsonObject> getMemberInfo(@RequestParam(required = true) String email) {
        response = ResponseJsonObject.builder()
            .code(HttpStatus.OK.value())
            .httpStatus(HttpStatus.OK)
            .message("내 이메일 조회 완료")
            .data(memberService.getMemberInfo(email))
            .build();
        //return new ResponseEntity<>(response,response.getHttpStatus());
        return ResponseEntity.ok()
            .body(response);
    }
}