package com.example.hibuddy.api.interfaces.request;

import com.example.hibuddy.api.domain.support.MailType;
import lombok.Getter;

@Getter
public class EmailRequest {
    private String email;
    private MailType type;
}
