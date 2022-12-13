package com.example.hibuddy.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Email type is undefined.")
public class EmailTypeUndefinedException extends RuntimeException {

    public EmailTypeUndefinedException() {
        super(MessageKey.WRONG_EMAIL_TYPE.getMessage());
    }
}
