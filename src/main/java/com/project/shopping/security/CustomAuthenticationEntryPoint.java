package com.project.shopping.security;

import com.project.shopping.Error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String exception = (String) request.getAttribute("exception");
        ErrorCode errorCode;


        log.debug("log: exception: {}", exception);


        if(exception.equals(ErrorCode.UnauthorizedException.getErrorCode())){
            errorCode = ErrorCode.UnauthorizedException;
            setResponse(response,errorCode);
            ;

        }
        if(exception.equals(ErrorCode.ExpirationException.getErrorCode())){
            errorCode = ErrorCode.ExpirationException;
            setResponse(response,errorCode);
            ;
        }

    }

    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println("{ \"message\" : \"" + errorCode.getMessage()
                + "\", \"code\" : \"" +  errorCode.getStatus()
                + "\", \"status\" : " + errorCode.getErrorCode()
                + "}");
    }
}
