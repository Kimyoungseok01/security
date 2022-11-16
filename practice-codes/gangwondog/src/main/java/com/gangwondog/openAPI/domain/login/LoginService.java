package com.gangwondog.openAPI.domain.login;

import com.gangwondog.openAPI.common.error.ErrorCode;
import com.gangwondog.openAPI.common.exception.CommonRuntimeException;
import com.gangwondog.openAPI.common.exception.TempJwtException;
import com.gangwondog.openAPI.config.security.TokenDto;
import com.gangwondog.openAPI.config.security.TokenProvider;
import com.gangwondog.openAPI.domain.login.dto.TokenRequestDto;
import com.gangwondog.openAPI.domain.user.UserEntityP;
import com.gangwondog.openAPI.domain.login.dto.MemberRequestDto;
import com.gangwondog.openAPI.domain.login.dto.MemberResponseDto;
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
public class LoginService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final LoginRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;


    @Transactional
    public MemberResponseDto signup(MemberRequestDto memberRequestDto) {
        if (userRepository.existsByEmail(memberRequestDto.getEmail())) {
            throw new CommonRuntimeException(ErrorCode.EXIST_USER.getMessage(), ErrorCode.EXIST_USER);
        }

        UserEntityP userEntityP = memberRequestDto.toMember(passwordEncoder);
        return MemberResponseDto.of(userRepository.save(userEntityP));
    }

    //@ApiOperation(value = "로그인", notes = "토큰 검증후 로그인", response = ResponseJsonObject.class)
    @Transactional
    public TokenDto login(MemberRequestDto memberRequestDto) {
        /*(여기에 SNS 식별자 유무 확인 로직 삽입)
        * 없을 시 예외처리 */
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
        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshTokenEntity);

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
        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));
        // 4. Refresh Token 일치하는지 검사
        if (!refreshTokenEntity.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new CommonRuntimeException(ErrorCode.NOTEQUAL_TOKEN.getMessage(), ErrorCode.NOTEQUAL_TOKEN);
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshTokenEntity newRefreshTokenEntity = refreshTokenEntity.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshTokenEntity);
        // 토큰 발급
        return tokenDto;
        }catch (TempJwtException e){
            throw new CommonRuntimeException(e.getMessage(),e.getErrorCode());
        }
    }
}
