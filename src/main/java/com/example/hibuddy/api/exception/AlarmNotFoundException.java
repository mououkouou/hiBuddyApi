package com.example.hibuddy.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Alarm is not found.")
public class AlarmNotFoundException extends RuntimeException {

    public AlarmNotFoundException() {
        super(MessageKey.ALARM_NOT_FOUND.getMessage());
    }
}
