package com.example.hibuddy.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Scrap is not found.")
public class ScrapNotFoundException extends RuntimeException {

    public ScrapNotFoundException() {
        super(MessageKey.SCRAP_NOTFOUND.getMessage());
    }
}
