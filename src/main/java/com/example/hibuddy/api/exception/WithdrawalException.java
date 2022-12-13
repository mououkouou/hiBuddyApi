package com.example.hibuddy.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "withdrawal data is less than 10 days.")
public class WithdrawalException extends RuntimeException {
    public WithdrawalException() {
        super(MessageKey.WITHDRAWAL_USER.getMessage());
    }
}
