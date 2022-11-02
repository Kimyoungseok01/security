package com.tutorial.jwtsecurity.config.security;

import com.tutorial.jwtsecurity.common.error.ErrorCode;
import com.tutorial.jwtsecurity.common.exception.TempJwtException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    //================ Spring Request 앞단에 붙힌 커스텀필터
    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String BEARER_PREFIX = "Bearer ";

    public static final String BASIC_PREFIX = "Basic ";

    private static final String BASIC_AUTH_CLIENT_ID = "gangwondog";

    private static final String BASIC_AUTH_CLIENT_SECRET = "gangwondog_diikanpp-9a0s-kkoa-aiikwnna900aa8c";
    private final TokenProvider tokenProvider;

    // 실제 필터링 로직은 doFilterInternal 에 들어감
    // JWT 토큰의 인증 정보를 현재 쓰레드의 SecurityContext 에 저장하는 역할 수행
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        // 1. Request Header 에서 토큰을 꺼냄
        //String jwt = resolveToken(request);
        String token = new String();
        String tokenType = request.getHeader(AUTHORIZATION_HEADER);
        //System.out.println("tokenType = " + tokenType);
        if (StringUtils.hasText(tokenType) && tokenType.startsWith(BEARER_PREFIX)){
            //System.out.println("토큰 값따라 찍히는 jwt");
            try {
                token = tokenType.substring(7);
                //System.out.println("jwt = " + jwt);
                // 2. validateToken 으로 토큰 유효성 검사
                // 정상 토큰이면 해당 토큰으로 Authentication 을 가져와서 SecurityContext 에 저장
                if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
                    Authentication authentication = tokenProvider.getAuthentication(token);
                    //저장하는 로직
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (TempJwtException e){
                request.setAttribute("errorCode",e.getErrorCode());
            } catch (Exception e){
                log.error("JwtFilter - doFilterInternal() 오류발생");
                log.error("token : {}", token);
                log.error("Exception Message : {}", e.getMessage());
                e.printStackTrace();
                request.setAttribute("errorCode", e.getMessage());
            }
        } else if (StringUtils.hasText(tokenType) && tokenType.startsWith(BASIC_PREFIX)) {
            try{
                token = tokenType.substring(6);
                System.out.println("jwt = " + token);
                byte[] decodedToken = Base64.getDecoder().decode(token);
                String decodeValue = new String(decodedToken, StandardCharsets.UTF_8);
                String basicToken = BASIC_AUTH_CLIENT_ID + ":" + BASIC_AUTH_CLIENT_SECRET;
                if (decodeValue.equals(basicToken)){
                    Authentication authentication = tokenProvider.getBasicAuthentication();
                    //저장하는 로직
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else if (!decodeValue.equals(basicToken)) {
                    request.setAttribute("errorCode",ErrorCode.WRONG_BASIC_TOKEN);
                }
            }catch (Exception e){
                log.error("JwtFilter - doFilterInternal() 오류발생");
                log.error("token : {}", token);
                log.error("Exception Message : {}", e.getMessage());
                e.printStackTrace();
                request.setAttribute("errorCode", e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
/*
    // Request Header 에서 토큰 정보를 꺼내오기
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
    private String resolveToken1(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        } else if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BASIC_PREFIX)) {
            return bearerToken.substring(6);
        }
        return null;
    }*/

}
