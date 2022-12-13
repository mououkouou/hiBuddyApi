package com.example.hibuddy.api.domain.repository;

import com.example.hibuddy.api.domain.chat.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}