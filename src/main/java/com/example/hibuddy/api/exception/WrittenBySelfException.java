package com.example.hibuddy.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED, reason = "This Post written by you.")
public class WrittenBySelfException extends RuntimeException {

    public WrittenBySelfException() {
        super(MessageKey.WRITTEN_BY_SELF.getMessage());
    }
}
