package com.example.hibuddy.api.domain.repository;

import com.example.hibuddy.api.domain.Companion;
import com.example.hibuddy.api.domain.User;
import com.example.hibuddy.api.domain.chat.Room;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Room, Long> {
    default boolean hasRoom(User companionWriter, User companionContacter, Companion companion) {
        return existsByCompanionWriterAndCompanionContacterAndCompanion(companionWriter, companionContacter, companion);
    }

    default Room getRoom(User companionWriter, User companionContacter, Companion companion) {
        return findByCompanionWriterAndCompanionContacterAndCompanion(companionWriter, companionContacter, companion);
    }

    default Optional<List<Room>> getRoomsByUser(User user) {
        return findAllByCompanionWriterAndLastMessageTimeIsNotNullOrCompanionContacterAndLastMessageTimeIsNotNull(user, user, Sort.by("lastMessageTime").descending());
    }

    @EntityGraph(attributePaths = {"companionWriter", "companionContacter", "companion"})
    Optional<Room> findById(Long Id);

    @EntityGraph(attributePaths = {"companionWriter", "companionContacter", "companion"})
    Room findByCompanionWriterAndCompanionContacterAndCompanion(User companionWriter, User companionContacter, Companion companion);

    boolean existsByCompanionWriterAndCompanionContacterAndCompanion(User companionWriter, User companionContacter, Companion companion);

    @EntityGraph(attributePaths = {"companionWriter", "companionContacter", "companion"})
    Optional<List<Room>> findAllByCompanionWriterAndLastMessageTimeIsNotNullOrCompanionContacterAndLastMessageTimeIsNotNull(User companionWriter, User companionContacter, Sort sort);
}
