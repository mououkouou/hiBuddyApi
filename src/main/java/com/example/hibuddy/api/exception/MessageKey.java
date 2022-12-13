package com.example.hibuddy.api.exception;

import lombok.Getter;

@Getter
public enum MessageKey {
    DUPLICATED_USER("중복된 이메일 입니다."),
    WRONG_PASSWORD("이메일 또는 비밀번호를 확인해주세요."),
    EXPIRED_REFRESH_TOKEN("재발급 토큰이 만료되었습니다."),
    WRONG_TOKEN("잘못된 토큰입니다."),
    WRONG_EMAIL_TYPE("잘못된 이메일 요청 타입입니다."),
    COMPANION_NOT_FOUND("현재 존재하지 않은 동행입니다."),
    SCRAP_NOTFOUND("존재하지 않은 스크랩입니다."),
    DUPLICATED_SCRAP("중복된 스크랩입니다."),
    ROOM_NOT_FOUND("현재 존재하지 않은 채팅방입니다."),
    WRITTEN_BY_SELF("본인이 작성한 게시글입니다."),
    DUPLICATED_PROMISE("중복된 확정입니다."),
    WITHDRAWAL_USER("탈퇴 후 10일 이하인 유저입니다."),
    ALARM_NOT_FOUND("알림이 존재하지 않습니다.");

    private final String message;

    MessageKey(String message) {
        this.message = message;
    }
}
