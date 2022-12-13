package com.example.hibuddy.api.interfaces.response;

import com.example.hibuddy.api.domain.Alarm;
import com.example.hibuddy.api.domain.support.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlarmResponse {
    private Long id;
    private Long userId;
    private Long roomId;
    private Long companyId;
    private Long crewId;
    private String alarmType;
    private String message;
    private String objectProfileUrl;
    private String alarmTime;
    private boolean read;

    public static AlarmResponse of(Alarm alarm) {
        AlarmResponse alarmResponse = AlarmResponse.builder()
                .id(alarm.getId())
                .userId(alarm.getUserId())
                .roomId(alarm.getAlarmType() == AlarmType.COMPANION_PROMISE || alarm.getAlarmType() == AlarmType.CREW_APPLY ? alarm.getObject().getRoomId() : null)
                .companyId(alarm.getAlarmType() == AlarmType.COMPANION_PROMISE ? alarm.getObject().getObjectId() : null)
                .crewId(alarm.getAlarmType() != AlarmType.COMPANION_PROMISE ? alarm.getObject().getObjectId() : null)
                .alarmType(alarm.getAlarmType() == AlarmType.COMPANION_PROMISE ? "동행" : "크루")
                .message(alarm.getMessage())
                .objectProfileUrl(alarm.getObject().getObjectProfileUrl())
                .read(alarm.isUserRead())
                .build();

        alarmResponse.parseMessageTime(alarm.getCreatedAt());

        return alarmResponse;
    }

    private void parseMessageTime(LocalDateTime beforeParseTime) {
        LocalDate now = LocalDate.now();

        String messageTime;
        if (now.isEqual(beforeParseTime.toLocalDate())) {
            messageTime = (beforeParseTime.getHour() < 10 ? "0" +beforeParseTime.getHour() : beforeParseTime.getHour()) + ":" +
                    (beforeParseTime.getMinute() < 10 ? "0" +beforeParseTime.getMinute() : beforeParseTime.getMinute());
        } else if (now.getYear() == beforeParseTime.getYear()) {
            messageTime = (beforeParseTime.getMonthValue() < 10 ? "0" + beforeParseTime.getMonthValue() : beforeParseTime.getMonthValue())
                    + "." + (beforeParseTime.getDayOfMonth() < 10 ? "0" + beforeParseTime.getDayOfMonth() : beforeParseTime.getDayOfMonth());

        } else {
            messageTime = beforeParseTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).replaceAll("-", ".");
        }

        this.alarmTime = messageTime;
    }
}
