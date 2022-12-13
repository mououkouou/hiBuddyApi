package com.example.hibuddy.api.interfaces.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ExceptionResponse {
    private String message;
    private HttpStatus status;
}
