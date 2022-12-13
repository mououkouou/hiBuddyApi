package com.example.hibuddy.api.configuration.jwt;

import com.example.hibuddy.api.interfaces.response.ExceptionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Getter
@Component
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String exception = (String) request.getAttribute("exception");
        if (exception == null) exception = "비로그인 시 이용할 수 없어요.";

        setResponse(response);

        ExceptionResponse exceptionResponse = new ExceptionResponse(exception, HttpStatus.UNAUTHORIZED);
        response.getWriter().print(convertObjectToJson(exceptionResponse));
    }

    private void setResponse(HttpServletResponse response) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        return mapper.writeValueAsString(object);
    }
}
