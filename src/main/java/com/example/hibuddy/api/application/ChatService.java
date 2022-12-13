package com.example.hibuddy.api.application;

import com.example.hibuddy.api.domain.Companion;
import com.example.hibuddy.api.domain.User;
import com.example.hibuddy.api.domain.chat.Room;
import com.example.hibuddy.api.domain.repository.ChatRepository;
import com.example.hibuddy.api.domain.repository.CompanionRepository;
import com.example.hibuddy.api.exception.CompanionNotFoundException;
import com.example.hibuddy.api.exception.RoomNotFoundException;
import com.example.hibuddy.api.exception.WrittenBySelfException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final CompanionRepository companionRepository;

    @Transactional
    public Room save(final User user, final Long id) {
        Companion companion = companionRepository.findById(id).orElseThrow(CompanionNotFoundException::new);
        User companionWriter = companion.getUser();

        if (companionWriter.getId() == user.getId()) throw new WrittenBySelfException();

        if (chatRepository.hasRoom(companionWriter, user, companion)) {
            return chatRepository.getRoom(companionWriter, user, companion);
        }

        Room room = Room.builder()
                .companion(companion)
                .companionContacter(user)
                .companionWriter(companionWriter)
                .contacterRead(true)
                .build();

        return chatRepository.save(room);
    }

    @Transactional
    public Room getRoomById(final User user, final Long id) {
        Room room = chatRepository.findById(id).orElseThrow(RoomNotFoundException::new);
        room.setRead(user.getId() == room.getCompanionContacter().getId());

        return room;
    }

    public List<Room> getRoomsByUserId(User user) {
        return chatRepository.getRoomsByUser(user).orElseThrow(RoomNotFoundException::new);
    }
}
