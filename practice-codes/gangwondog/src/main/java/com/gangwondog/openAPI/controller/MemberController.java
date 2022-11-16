package com.gangwondog.openAPI.controller;

import com.gangwondog.openAPI.common.response.ResponseJsonObject;
import com.gangwondog.openAPI.domain.user.UserService;
import com.gangwondog.openAPI.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final UserService userService;

    private ResponseJsonObject response;

    @GetMapping("/me")
    public ResponseEntity<ResponseJsonObject> getMyMemberInfo() {
        //return ResponseEntity.ok(memberService.getMyInfo());
        response = ResponseJsonObject.builder()
            //.code(HttpStatus.OK.value())
            //.httpStatus(HttpStatus.OK)
            .message("현재 로그인 정보 조회 완료")
            .data(userService.getMyInfo())
            .build();
        return new ResponseEntity<>(response,response.getHttpStatus());
    }

    @GetMapping("/email")
    public ResponseEntity<ResponseJsonObject> getMemberInfo(@RequestParam(required = true) String email) {
        response = ResponseJsonObject.builder()
            //.code(HttpStatus.OK.value())
            //.httpStatus(HttpStatus.OK)
            .message("내 이메일 조회 완료")
            .data(userService.getMemberInfo(email))
            .build();
        //return new ResponseEntity<>(response,response.getHttpStatus());
        return ResponseEntity.ok()
            .body(response);
    }

    @GetMapping("/page")
    public ResponseEntity<ResponseJsonObject> getMemberPage(@RequestParam Integer page,@RequestParam Integer size,@RequestParam String sort,@RequestParam String email){
        //Page<Member> results = memberService.getMemberPage(pageRequest, email);
        PageRequest pageable = CommonUtil.pagable(page, size, sort);
        if(pageable.getSort().isUnsorted()){
            pageable = pageable.withSort(Sort.Direction.ASC, "email");
        }

        response = ResponseJsonObject.builder()
            .message("페이지완료")
            .data(userService.getMemberPage(pageable, email))
            .build();

        return ResponseEntity.ok()
            .body(response);
    }
}