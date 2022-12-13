package com.example.hibuddy.api.domain.chat;

import com.example.hibuddy.api.domain.Companion;
import com.example.hibuddy.api.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "room", indexes = {
        @Index(name = "user_companion_index", columnList = "writer_id, contacter_id, companion_id"),
        @Index(name = "user_id_index", columnList = "writer_id, contacter_id"),
        @Index(name = "user_message_time_index", columnList = "writer_id, contacter_id, lastMessageTime")
})
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();

    private String lastMessage;

    private LocalDateTime lastMessageTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "writer_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User companionWriter;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "contacter_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User companionContacter;

    private boolean writerRead;

    private boolean contacterRead;

    private boolean writerDelete;

    private boolean contacterDelete;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "companion_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Companion companion;

    @Builder
    public Room(Companion companion, User companionContacter, User companionWriter, boolean contacterRead) {
        this.companion = companion;
        this.companionContacter = companionContacter;
        this.companionWriter = companionWriter;
        this.contacterRead = contacterRead;
    }

    public void setRead(boolean isFromContacter) {
        this.contacterRead = isFromContacter;
        this.writerRead = !isFromContacter;
    }

    public void setLastMessage(Message message) {
        this.lastMessage = message.getContent();
        this.lastMessageTime = message.getCreatedAt();
    }
}
