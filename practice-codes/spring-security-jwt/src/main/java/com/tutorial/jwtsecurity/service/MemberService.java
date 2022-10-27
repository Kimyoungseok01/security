package com.tutorial.jwtsecurity.service;

import com.tutorial.jwtsecurity.Exception.CommonRuntimeException;
import com.tutorial.jwtsecurity.Exception.ErrorCode;
import com.tutorial.jwtsecurity.Exception.NotFoundUsernameException;
import com.tutorial.jwtsecurity.controller.dto.MemberResponseDto;
import com.tutorial.jwtsecurity.repository.MemberRepository;
import com.tutorial.jwtsecurity.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberResponseDto getMemberInfo(String email) {
        return memberRepository.findByEmail(email)
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new NotFoundUsernameException(ErrorCode.UNAUTHORIZED.getMessage(), ErrorCode.UNAUTHORIZED));
    }

    // 현재 SecurityContext 에 있는 유저 정보 가져오기
    @Transactional(readOnly = true)
    public MemberResponseDto getMyInfo() {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new CommonRuntimeException(ErrorCode.UNAUTHORIZED.getMessage(), ErrorCode.UNAUTHORIZED));
    }
}
