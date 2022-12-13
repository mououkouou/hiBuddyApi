package com.example.hibuddy.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Promise is duplicated.")
public class DuplicatedPromiseException extends RuntimeException{

    public DuplicatedPromiseException() {
        super(MessageKey.DUPLICATED_PROMISE.getMessage());
    }
}
