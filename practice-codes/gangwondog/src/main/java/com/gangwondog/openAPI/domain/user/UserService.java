package com.gangwondog.openAPI.domain.user;

import com.gangwondog.openAPI.common.error.ErrorCode;
import com.gangwondog.openAPI.common.exception.CommonRuntimeException;
import com.gangwondog.openAPI.common.exception.NotFoundUsernameException;
import com.gangwondog.openAPI.domain.login.LoginRepository;
import com.gangwondog.openAPI.domain.login.dto.MemberResponseDto;
import com.gangwondog.openAPI.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final LoginRepository userRepository;

    @Transactional(readOnly = true)
    public MemberResponseDto getMemberInfo(String email) {
        return userRepository.findByEmail(email)
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new NotFoundUsernameException(ErrorCode.UNAUTHORIZED.getMessage(), ErrorCode.UNAUTHORIZED));
    }

    // 현재 SecurityContext 에 있는 유저 정보 가져오기
    @Transactional(readOnly = true)
    public MemberResponseDto getMyInfo() {
/*      Optional<String> id = memberRepository.findById(SecurityUtil.getCurrentMemberId())
          .map(MemberResponseDto::ot);
      }*/
        return userRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new CommonRuntimeException(ErrorCode.UNAUTHORIZED.getMessage(), ErrorCode.UNAUTHORIZED));
    }

  public Page<UserEntityP> getMemberPage(PageRequest pageRequest, String email) {
        Page<UserEntityP> members = userRepository.getMemberPage(pageRequest,email);
        if(members.getContent().size() == 0 || members.getContent().get(0) == null){
          throw new NotFoundUsernameException(ErrorCode.NO_DATA.getMessage(), ErrorCode.NO_DATA);
        }
        return members;
  }
}
