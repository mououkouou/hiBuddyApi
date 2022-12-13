package com.example.hibuddy.api.application;

import com.example.hibuddy.api.domain.Companion;
import com.example.hibuddy.api.domain.support.Mountain;
import com.example.hibuddy.api.domain.User;
import com.example.hibuddy.api.domain.chat.Room;
import com.example.hibuddy.api.domain.repository.ChatRepository;
import com.example.hibuddy.api.domain.repository.CompanionRepository;
import com.example.hibuddy.api.domain.repository.MessageRepository;
import com.example.hibuddy.api.domain.repository.UserRepository;
import com.example.hibuddy.api.domain.support.GenderType;
import com.example.hibuddy.api.interfaces.request.MessageRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class ChatServiceTest {

    @Autowired
    ChatService chatService;

    @Autowired
    MessageService messageService;

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CompanionRepository companionRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAllInBatch();
        chatRepository.deleteAllInBatch();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAllInBatch();
        chatRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("save는 User와 companionId를 받아 Room 객체를 저장합니다.")
    public void testSave() throws Exception {
        //given
        User user = createUser("companionContacter@email.com", "companionContacter");
        Companion companion = createCompanion();
        Long companionId = companion.getId();

        //when
        Room saved = chatService.save(user, companionId);

        //then
        Room room = chatRepository.findById(saved.getId()).get();

        assertEquals(saved.getId(), room.getId());
        assertEquals(saved.getCompanionWriter().getId(), room.getCompanionWriter().getId());
        assertEquals(saved.getCompanionContacter().getId(), room.getCompanionContacter().getId());
    }

    @Test
    @DisplayName("getRoomById는 User와 roomId를 통해 room을 리턴합니다.")
    public void testGetRoomByIdSuccess() throws Exception {
        //given
        User user = createUser("companionCreater@email.com", "companionCreater");
        Long roomId = createRoom().getId();

        //when
        Room findRoom = chatService.getRoomById(user, roomId);

        //then
        Room room = chatRepository.findById(findRoom.getId()).get();

        assertEquals(findRoom.getId(), room.getId());
        assertEquals(findRoom.getCompanionWriter().getId(), room.getCompanionWriter().getId());
        assertEquals(findRoom.getCompanionContacter().getId(), room.getCompanionContacter().getId());
    }

    @Test
    public void 유저삭제시_채팅삭제처리() throws Exception {
        //given
        Room room = createRoom();
        User user = room.getCompanionContacter();
        Long roomId = room.getId();

        messageService.save(roomId,"send message", user);

        userRepository.delete(user);

        //when then
        List<Room> rooms = chatRepository.getRoomsByUser(user).get();

        //then
        assertTrue(rooms.isEmpty());
    }

    @Test
    @DisplayName("getRoomById는 메세지 읽음 처리를 합니다.")
    public void testGetRoomByIdRead() throws Exception {
        //given
        User loginUser = createUser("companionCreater@email.com", "companionCreater");

        Room saveRoom = createRoom();
        Long roomId = saveRoom.getId();
        User messageSendUser = saveRoom.getCompanionContacter();

        messageService.save(roomId, "send message", messageSendUser);

        //when
        Room findRoom = chatService.getRoomById(loginUser, roomId);

        //then
        Room room = chatRepository.findById(findRoom.getId()).get();

        assertTrue(room.isWriterRead());
    }

    private User createUser(String email, String nickname) {
        User user = User.builder()
                .email(email)
                .gender(GenderType.MAN)
                .birth(LocalDate.of(2020, 1, 1))
                .password(passwordEncoder.encode("dummy"))
                .nickname(nickname)
                .build();
        return userRepository.save(user);
    }

    private Companion createCompanion() {
        Mountain mountain = new Mountain("관악산", "서울시 신림동 1688-12", "http://tong.visitkorea.or.kr/cms/resource/30/1857230_image2_1.jpg");
        User user = createUser("companionCreater@email.com", "companionCreater");
        Companion companion = Companion.builder()
                .user(user)
                .mountain(mountain)
                .subject("관악산 같이 오르실 분 구해요.")
                .detail("관악산 같이 오르실 분 구해요.")
                .accompanyingDate(LocalDate.now())
                .minRecruitment(2)
                .maxRecruitment(5)
                .fixedNumber(0)
                .minAge(20)
                .maxAge(30)
                .preferGenderType(null)
                .build();

        return companionRepository.save(companion);

    }

    private Room createRoom() {
        User user = createUser("companionContacter@email.com", "companionContacter");
        Companion companion = createCompanion();

        Room room = Room.builder()
                .companion(companion)
                .companionContacter(user)
                .companionWriter(companion.getUser())
                .contacterRead(true)
                .build();

        return chatRepository.save(room);
    }
}