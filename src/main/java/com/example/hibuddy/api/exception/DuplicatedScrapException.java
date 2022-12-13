package com.example.hibuddy.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Scrap is duplicated.")
public class DuplicatedScrapException extends RuntimeException {

    public DuplicatedScrapException() {
        super(MessageKey.DUPLICATED_SCRAP.getMessage());
    }
}
