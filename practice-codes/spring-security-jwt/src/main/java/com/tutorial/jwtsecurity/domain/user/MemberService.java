package com.tutorial.jwtsecurity.domain.user;

import com.tutorial.jwtsecurity.common.exception.CommonRuntimeException;
import com.tutorial.jwtsecurity.common.error.ErrorCode;
import com.tutorial.jwtsecurity.common.exception.NotFoundUsernameException;
import com.tutorial.jwtsecurity.domain.user.dto.MemberResponseDto;
import com.tutorial.jwtsecurity.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

  public Page<Member> getMemberPage(PageRequest pageRequest, String email) {
        Page<Member> members = memberRepository.getMemberPage(pageRequest,email);
        if(members.getContent().size() == 0 || members.getContent().get(0) == null){
          throw new NotFoundUsernameException(ErrorCode.NO_DATA.getMessage(), ErrorCode.NO_DATA);
        }
        return members;
  }
}
