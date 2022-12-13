package com.example.hibuddy.api.domain.support;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum MailType {
    REGISTER("[하이버디] 가입 인증 메일"),
    PASSWORD("[하이버디] 비밀번호 찾기");

    private String text;
}
