package com.example.hibuddy.api.interfaces.response;

import com.example.hibuddy.api.domain.User;
import com.example.hibuddy.api.domain.chat.Message;
import com.example.hibuddy.api.domain.chat.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomView {
    private Long id;
    private Long companionId;
    private Long contacterId;
    private Long opponentUserId;
    private String opponentNickname;
    private String opponentProfilePictureUrl;
    private List<MessageResponse> messages;
    private boolean promiseCompanion;

    public static RoomView of(Room room, User user, boolean promiseCompanion) {
        RoomView roomView = RoomView.builder()
                .id(room.getId())
                .companionId(room.getCompanion().getId())
                .contacterId(room.getCompanionContacter().getId())
                .promiseCompanion(promiseCompanion)
                .build();

        roomView.setOpponent(room, user);
        roomView.setMessages(room.getMessages());

        return roomView;
    }

    private void setOpponent(Room room, User user) {
        User opponentUser = user.getId() == room.getCompanionWriter().getId() ? room.getCompanionContacter() : room.getCompanionWriter();

        this.opponentUserId = opponentUser.getId();
        this.opponentNickname = opponentUser.getNickname();
        this.opponentProfilePictureUrl = opponentUser.getProfilePictureUrl();
    }

    private void setMessages(List<Message> messages) {
        this.messages = messages.stream()
                .map(MessageResponse::of)
                .collect(Collectors.toList());
    }
}
