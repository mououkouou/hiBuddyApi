
package com.example.hibuddy.api.application;

import com.example.hibuddy.api.domain.Alarm;
import com.example.hibuddy.api.domain.Companion;
import com.example.hibuddy.api.domain.User;
import com.example.hibuddy.api.domain.chat.Room;
import com.example.hibuddy.api.domain.repository.AlarmRepository;
import com.example.hibuddy.api.domain.repository.ChatRepository;
import com.example.hibuddy.api.domain.repository.CompanionRepository;
import com.example.hibuddy.api.domain.repository.UserRepository;
import com.example.hibuddy.api.domain.support.AlarmObject;
import com.example.hibuddy.api.domain.support.AlarmType;
import com.example.hibuddy.api.domain.support.GenderType;
import com.example.hibuddy.api.domain.support.Mountain;
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
class AlarmServiceTest {
    @Autowired
    AlarmService alarmService;

    @Autowired
    AlarmRepository alarmRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CompanionRepository companionRepository;

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        alarmRepository.deleteAll();
        userRepository.deleteAll();
        companionRepository.deleteAll();
        chatRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        alarmRepository.deleteAll();
        userRepository.deleteAll();
        companionRepository.deleteAll();
        chatRepository.deleteAll();
    }

    @Test
    @DisplayName("save는 AlarmType로 구분하여 Alarm객체를 저장합니다.")
    public void testSave() throws Exception {
        //given
        User user = createUser("user@user.com", "user");
        User opposite = createUser("opposite@oppsite.com", "opposite");

        Companion companion = createCompanion(user);

        Room room = createRoom(opposite, companion);
        //when
        alarmService.save(user, opposite.getId(), companion.getId(), AlarmType.COMPANION_PROMISE);

        //then
        Alarm alarm = alarmRepository.findAllByUserId(user.getId()).get().get(0);

        assertNotNull(alarm);
        assertEquals(alarm.getAlarmType(), AlarmType.COMPANION_PROMISE);
        assertEquals(alarm.getObject().getRoomId(), room.getId());
    }

    @Test
    @DisplayName("getAlarmsByUserId은 userId를 통해 alarm들을 리턴합니다.")
    public void testGetAlarmsByUserId() throws Exception {
        //given
        Long userId = 0L;

        AlarmObject alarmObjectA = AlarmObject.builder()
                .objectId(0L)
                .roomId(0L)
                .build();

        Alarm alarmA = Alarm.builder()
                .userId(userId)
                .message("동행이 확정되었습니다.")
                .alarmType(AlarmType.COMPANION_PROMISE)
                .userRead(false)
                .object(alarmObjectA)
                .build();

        AlarmObject alarmObjectB = AlarmObject.builder()
                .objectId(0L)
                .build();

        Alarm alarmB = Alarm.builder()
                .userId(userId)
                .message("크루크루님이 크루원이 되셨어요.")
                .alarmType(AlarmType.CREW_JOIN)
                .userRead(false)
                .object(alarmObjectB)
                .build();

        List<Alarm> savedAlarms = alarmRepository.saveAll(List.of(alarmA, alarmB));

        //when
        List<Alarm> alarms = alarmService.getAlarmsByUserId(userId);

        //then
        assertEquals(2, alarms.size());
        assertEquals(userId, alarms.get(0).getUserId());
        assertEquals(savedAlarms.get(0).getId(), alarms.get(0).getId());
    }

    @Test
    @DisplayName("readAlarm은 alarmId로 해당 alarm의 userRead값을 true로 바꿉니다.")
    public void testReadAlarm() throws Exception {
        //given
        AlarmObject alarmObject = AlarmObject.builder()
                .objectId(0L)
                .roomId(0L)
                .build();

        Alarm alarm = Alarm.builder()
                .userId(0L)
                .message("동행이 확정되었습니다.")
                .alarmType(AlarmType.COMPANION_PROMISE)
                .userRead(false)
                .object(alarmObject)
                .build();

        Alarm savedAlarm = alarmRepository.save(alarm);

        //when
        alarmService.readAlarm(savedAlarm.getId());
        Alarm readAlarm = alarmRepository.findById(savedAlarm.getId()).get();

        //then
        assertEquals(true, readAlarm.isUserRead());
        assertEquals(false, savedAlarm.isUserRead());
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

    private Companion createCompanion(User user) {
        Mountain mountain = new Mountain("관악산", "서울시 신림동 1688-12", "http://tong.visitkorea.or.kr/cms/resource/30/1857230_image2_1.jpg");
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

    private Room createRoom(User opposite, Companion companion) {
        Room room = Room.builder()
                .companion(companion)
                .companionContacter(opposite)
                .companionWriter(companion.getUser())
                .contacterRead(true)
                .build();

        return chatRepository.save(room);
    }
}
