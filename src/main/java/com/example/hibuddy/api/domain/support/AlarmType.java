package com.example.hibuddy.api.domain.support;

public enum AlarmType {
    CREW_APPLY("님이 참여 신청을 보내셨어요!"),
    CREW_WITHDRAWAL("님이 크루를 탈퇴하셨어요."),
    CREW_JOIN("님이 크루원이 되셨어요."),
    COMPANION_PROMISE("님과의 동행이 확정되었어요.");

    private String message;

    AlarmType(String message) {
        this.message = message;
    }

    public static String makeMessage(AlarmType alarmType, String nickname) {
        return nickname + alarmType.message;
    }
}
