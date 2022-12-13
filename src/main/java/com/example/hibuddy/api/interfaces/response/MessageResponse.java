package com.example.hibuddy.api.interfaces.response;

import com.example.hibuddy.api.domain.chat.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    private Long id;
    private boolean fromContacter;
    private String content;
    private String createAt;

    public static MessageResponse of(Message message) {
        MessageResponse messageResponse = MessageResponse.builder()
                .id(message.getId())
                .fromContacter(message.isFromContacter())
                .content(message.getContent())
                .build();

        messageResponse.parseMessageTime(message.getCreatedAt());

        return messageResponse;
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
        this.createAt = messageTime;
    }
}
