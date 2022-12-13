package com.example.hibuddy.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Companion is not found.")
public class CompanionNotFoundException extends RuntimeException {

    public CompanionNotFoundException() {
        super(MessageKey.COMPANION_NOT_FOUND.getMessage());
    }
}
