package com.gangwondog.openAPI.domain.login.dto;

import com.gangwondog.openAPI.domain.user.UserEntityP;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {
    private String email;

    public static MemberResponseDto of(UserEntityP userEntityP) {
        return new MemberResponseDto(userEntityP.getEmail());
    }

    public static String ot(UserEntityP userEntityP){
        return userEntityP.getEmail();
    }
}
