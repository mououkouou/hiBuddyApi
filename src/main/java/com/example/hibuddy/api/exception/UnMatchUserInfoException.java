package com.example.hibuddy.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UnMatchUserInfoException extends RuntimeException {

    public UnMatchUserInfoException(MessageKey messageKey) {
        super(messageKey.getMessage());
    }
}
