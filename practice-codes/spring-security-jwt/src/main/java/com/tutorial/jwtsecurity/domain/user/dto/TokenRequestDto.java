package com.tutorial.jwtsecurity.domain.user.dto;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenRequestDto {
    @ApiParam(value = "리프레시토큰", required = true, example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiYXV0aCI6IlJPTEVfVVNFUiIsImV4cCI6MTY2NjgzODQ1OH0.4uMnjHH2T0nhdKqKkCiU6Ph2iq6HVsKsGzYIE1oCbuVNH6NK8l5idhibxYEHxkkLiECXv8LKtDLwExP-WMDSbQ")
    private String refreshToken;
}
