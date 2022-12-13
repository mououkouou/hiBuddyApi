package com.example.hibuddy.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class RefreshTokenExpiredException extends RuntimeException {

    public RefreshTokenExpiredException(MessageKey messageKey) {
        super(messageKey.getMessage());
    }
}
