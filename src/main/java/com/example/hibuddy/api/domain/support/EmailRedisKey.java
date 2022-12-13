package com.example.hibuddy.api.domain.support;

import com.example.hibuddy.api.exception.EmailTypeUndefinedException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum EmailRedisKey {
    EMAIL_CONFIRM("email::", MailType.REGISTER),
    PASSWORD("password::", MailType.PASSWORD);

    private String prefix;
    private MailType type;

    public static EmailRedisKey getByType(MailType type) {
        return Stream.of(values())
                .filter(key -> key.type.equals(type))
                .findAny()
                .orElseThrow(EmailTypeUndefinedException::new);
    }
}
