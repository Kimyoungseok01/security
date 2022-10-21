package com.tutorial.jwtsecurity.controller.dto;

import com.tutorial.jwtsecurity.entity.Authority;
import com.tutorial.jwtsecurity.entity.Member;
import io.swagger.annotations.ApiParam;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {
    @ApiParam(value = "이메일",required = true, example = "test@test.net")
    private String email;
    @ApiParam(value = "비밀번호",required = true, example = "1q2w3e4r")
    private String password;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .authority(Authority.ROLE_USER)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
