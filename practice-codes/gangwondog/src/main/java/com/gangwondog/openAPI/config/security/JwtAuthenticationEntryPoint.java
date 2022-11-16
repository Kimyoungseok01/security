package com.gangwondog.openAPI.config.security;

import com.gangwondog.openAPI.common.error.ErrorCode;
import javax.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    // ========================== 인증 정보가 없을 때 401 에러 ============================
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException,ServletException {
        log.error(authException.getLocalizedMessage());
        ErrorCode errorCode = (ErrorCode) request.getAttribute("errorCode");
        System.out.println("errorCode = " + errorCode);
        if (errorCode == ErrorCode.UNKNOWN_ERROR){
            setResponse(response,ErrorCode.UNKNOWN_ERROR);
        } else if (errorCode == null) {
            setResponse(response,ErrorCode.UNAUTHORIZED);
        } else if(errorCode == ErrorCode.WRONG_TYPE_TOKEN ) {
            //잘못된 타입의 토큰인 경우
            setResponse(response, ErrorCode.WRONG_TYPE_TOKEN);
        } else if(errorCode == ErrorCode.EXPIRED_TOKEN ) {
            //토큰 만료된 경우
            setResponse(response, ErrorCode.EXPIRED_TOKEN);
        } else if(errorCode == ErrorCode.UNSUPPORTED_TOKEN) {
            //지원되지 않는 토큰인 경우
            setResponse(response, ErrorCode.UNSUPPORTED_TOKEN);
        } else if (errorCode == ErrorCode.WRONG_BASIC_TOKEN) {
            //베이직 토큰이 잘못되었을 때
            setResponse(response, ErrorCode.WRONG_BASIC_TOKEN);
        } else {
            setResponse(response, ErrorCode.WRONG_TOKEN);
        }
    }

    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",errorCode.getCode());
        jsonObject.put("HttpStatus",errorCode.getHttpStatus().toString());
        jsonObject.put("message",errorCode.getMessage());
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(jsonObject.toJSONString());
        response.setStatus(errorCode.getHttpStatus().value());
    }

}
