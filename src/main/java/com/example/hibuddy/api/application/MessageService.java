package com.example.hibuddy.api.application;

import com.example.hibuddy.api.domain.User;
import com.example.hibuddy.api.domain.chat.Message;
import com.example.hibuddy.api.domain.chat.Room;
import com.example.hibuddy.api.domain.repository.ChatRepository;
import com.example.hibuddy.api.domain.repository.MessageRepository;
import com.example.hibuddy.api.domain.support.MessageVisibleType;
import com.example.hibuddy.api.exception.RoomNotFoundException;
import com.example.hibuddy.api.interfaces.request.MessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;

    @Transactional
    public Message save(final Long roomId, final String content, final User user) {
        Room room = chatRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
        boolean fromContacter = (room.getCompanionContacter().getId() == user.getId());

        Message message = Message.builder()
                .room(room)
                .content(content)
                .fromContacter(fromContacter)
                .messageVisible(MessageVisibleType.BOTH)
                .build();

        Message result = messageRepository.save(message);
        room.setRead(fromContacter);
        room.getMessages().add(result);
        room.setLastMessage(message);

        return result;
    }
}
