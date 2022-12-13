package com.example.hibuddy.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User is not found.")
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super(MessageKey.DUPLICATED_USER.getMessage());
    }
}
