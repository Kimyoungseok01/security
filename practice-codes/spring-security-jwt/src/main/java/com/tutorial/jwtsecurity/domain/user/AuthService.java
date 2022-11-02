package com.tutorial.jwtsecurity.domain.user;

import com.tutorial.jwtsecurity.common.exception.CommonRuntimeException;
import com.tutorial.jwtsecurity.common.error.ErrorCode;
import com.tutorial.jwtsecurity.common.exception.TempJwtException;
import com.tutorial.jwtsecurity.domain.user.dto.MemberRequestDto;
import com.tutorial.jwtsecurity.domain.user.dto.MemberResponseDto;
import com.tutorial.jwtsecurity.domain.user.dto.TokenRequestDto;
import com.tutorial.jwtsecurity.config.security.TokenDto;
import com.tutorial.jwtsecurity.config.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;


    @Transactional
    public MemberResponseDto signup(MemberRequestDto memberRequestDto) {
        if (memberRepository.existsByEmail(memberRequestDto.getEmail())) {
            throw new CommonRuntimeException(ErrorCode.EXIST_USER.getMessage(), ErrorCode.EXIST_USER);
        }

        Member member = memberRequestDto.toMember(passwordEncoder);
        return MemberResponseDto.of(memberRepository.save(member));
    }

    //@ApiOperation(value = "로그인", notes = "토큰 검증후 로그인", response = ResponseJsonObject.class)
    @Transactional
    public TokenDto login(MemberRequestDto memberRequestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberRequestDto.toAuthentication();
        System.out.println("authenticationToken = " + authenticationToken);
        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        System.out.println("authentication.getName() = " + authentication.getName());

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenDto;
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        try {
        // 1.Refresh Token 검증
        // access Token 검증시에 예외를 던져버림
            tokenProvider.validateToken(tokenRequestDto.getRefreshToken());
        // 2. Refresh Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getRefreshToken());
        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        //우선 로그아웃 기능 추가 안할거라 상관없
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));
        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new CommonRuntimeException(ErrorCode.NOTEQUAL_TOKEN.getMessage(), ErrorCode.NOTEQUAL_TOKEN);
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);
        // 토큰 발급
        return tokenDto;
        }catch (TempJwtException e){
            throw new CommonRuntimeException(e.getMessage(),e.getErrorCode());
        }
    }
}
