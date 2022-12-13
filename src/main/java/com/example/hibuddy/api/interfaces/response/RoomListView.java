package com.example.hibuddy.api.interfaces.response;

import com.example.hibuddy.api.domain.User;
import com.example.hibuddy.api.domain.chat.Room;
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
public class RoomListView {
    private Long id;
    private Long companionId;
    private String companionSubject;
    private String opponentNickname;
    private String lastMessage;
    private String lastMessageTime;
    private String opponentProfilePictureUrl;
    private boolean lastMessageRead;

    public static RoomListView of(Room room, User user) {
        RoomListView roomListView = RoomListView.builder()
                .id(room.getId())
                .companionId(room.getCompanion().getId())
                .companionSubject(room.getCompanion().getSubject())
                .lastMessage(room.getLastMessage())
                .build();

        roomListView.setOpponent(room, user);
        roomListView.parseLastMessageTime(room.getLastMessageTime());
        roomListView.isLastMessageRead(room, user);

        return roomListView;
    }

    private void setOpponent(Room room, User user) {
        User opponentUser = (user.getId() == room.getCompanionWriter().getId() ? room.getCompanionContacter() : room.getCompanionWriter());
        this.opponentNickname = opponentUser.getNickname();
        this.opponentProfilePictureUrl = opponentUser.getProfilePictureUrl();
    }

    private void parseLastMessageTime(LocalDateTime beforeParseTime) {
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

        this.lastMessageTime = messageTime;
    }

    private void isLastMessageRead(Room room, User user) {
        this.lastMessageRead = (user.getId() == room.getCompanionContacter().getId() ? room.isContacterRead() : room.isWriterRead());
    }

}
