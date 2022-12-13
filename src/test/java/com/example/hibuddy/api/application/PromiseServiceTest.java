package com.example.hibuddy.api.application;

import com.example.hibuddy.api.domain.Companion;
import com.example.hibuddy.api.domain.chat.Room;
import com.example.hibuddy.api.domain.repository.ChatRepository;
import com.example.hibuddy.api.domain.support.Mountain;
import com.example.hibuddy.api.domain.Promise;
import com.example.hibuddy.api.domain.User;
import com.example.hibuddy.api.domain.repository.CompanionRepository;
import com.example.hibuddy.api.domain.repository.PromiseRepository;
import com.example.hibuddy.api.domain.repository.UserRepository;
import com.example.hibuddy.api.domain.support.GenderType;
import com.example.hibuddy.api.domain.support.PromiseStatusType;
import com.example.hibuddy.api.exception.DuplicatedPromiseException;
import com.example.hibuddy.api.interfaces.request.CompanionRequest;
import com.example.hibuddy.api.interfaces.request.PromiseRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class PromiseServiceTest {

    @Autowired
    PromiseService promiseService;

    @Autowired
    PromiseRepository promiseRepository;

    @Autowired
    CompanionRepository companionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChatRepository chatRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        promiseRepository.deleteAll();
        chatRepository.deleteAll();
        companionRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        promiseRepository.deleteAll();
        chatRepository.deleteAll();
        companionRepository.deleteAll();
    }

    @Test
    @DisplayName("save는 PromiseRequest와 userId를 받아 Promise 객체를 저장합니다.")
    public void testSaveSuccess() throws Exception {
        //given
        User user = createUser();
        Long oppositeUserId = 1L;
        Long companionId = 0L;
        Room room = createRoom();
        Long roomId = room.getId();
        PromiseRequest promiseRequest = new PromiseRequest(oppositeUserId, companionId, roomId);

        //when
        Promise saved = promiseService.save(promiseRequest, user);

        //then
        Promise promise = promiseRepository.findById(saved.getId()).get();

        assertEquals(saved.getId(), promise.getId());
        assertEquals(saved.getFirstPromiseUserId(), promise.getFirstPromiseUserId());
        assertEquals(saved.getPromiseStatusType(), promise.getPromiseStatusType());
        assertEquals(saved.getPromiseStatusType(), PromiseStatusType.ONE);
        assertEquals(saved.getSecondPromiseUserId(), promise.getSecondPromiseUserId());
        assertNull(saved.getSecondPromiseUserId());
        assertEquals(saved.getCompanionId(), promise.getCompanionId());
    }

    @Test
    @DisplayName("save는 userId와 companionId가 이미 존재하는 경우는 DuplicatedPromiseException을 발생시킨다. ")
    public void testSaveFail() throws Exception {
        //given
        User user = createUser();
        Long oppositeUserId = 1L;
        Long companionId = 0L;
        Room room = createRoom();
        Long roomId = room.getId();
        PromiseRequest promiseRequest = new PromiseRequest(oppositeUserId, companionId, roomId);

        Promise saved = promiseService.save(promiseRequest, user);
        //when then
        assertThrows(DuplicatedPromiseException.class, () -> promiseService.save(promiseRequest, user));

    }


    @Test
    @DisplayName("save는 oppositeUserId와 companionId가 이미 존재하는 경우 해당 Promise의 secondUserId와 promiseStatusType을 update한다.")
    public void testSaveSecondUser() throws Exception {
        //given
        Room room = createRoom();

        Long companionId = room.getCompanion().getId();
        User user = room.getCompanionWriter();

        Long oppositeUserId = room.getCompanionContacter().getId();
        Long roomId = room.getId();

        Promise firstUserPromise = createPromise(user.getId(), oppositeUserId, companionId, roomId);

        PromiseRequest promiseRequest = new PromiseRequest(oppositeUserId, companionId, roomId);

        //when
        Promise secondUserPromise = promiseService.save(promiseRequest, user);

        //then
        Promise promise = promiseRepository.findById(secondUserPromise.getId()).get();

        assertEquals(firstUserPromise.getId(), secondUserPromise.getId());
        assertEquals(firstUserPromise.getFirstPromiseUserId(), secondUserPromise.getFirstPromiseUserId());
        assertEquals(promise.getSecondPromiseUserId(), secondUserPromise.getSecondPromiseUserId());
        assertEquals(secondUserPromise.getFirstPromiseUserId(), oppositeUserId);
        assertEquals(secondUserPromise.getSecondPromiseUserId(), user.getId());
        assertEquals(firstUserPromise.getPromiseStatusType(), PromiseStatusType.ONE);
        assertEquals(secondUserPromise.getPromiseStatusType(), PromiseStatusType.BOTH);
        assertEquals(promise.getPromiseStatusType(), secondUserPromise.getPromiseStatusType());
    }


    @Test
    @DisplayName("countPromises는 userId를 받아 해당 유저의 양쪽 확정된 Promise 갯수를 리턴합니다.")
    @Transactional
    public void testPromisesCount() throws Exception {
        //given
        List<Long> companionIds = List.of(0L, 1L, 2L);
        Long userAId = 0L;
        Long userBId = 1L;
        Long roomId = 0L;
        List<Promise> promises = promiseRepository.saveAll(companionIds.stream()
                .map(c -> Promise.of(new PromiseRequest(userBId, c, roomId), userAId))
                .collect(Collectors.toList()));

        promises.stream().forEach(p -> p.setSecondPromiseUserId(userBId));

        //when
        Long count = promiseService.countPromises(userAId);

        //then
        assertEquals(companionIds.size(), count);
    }

    @Test
    @DisplayName("getPromisesById는 userId를 받아 해당 유저의 양쪽 확정된 Companion을 리턴합니다.")
    @Transactional
    public void testGetPromisesSuccess() throws Exception {
        //given
        User userA = createUser();
        User userB = createUser();
        Long roomId = 0L;
        Mountain mountain = new Mountain("관악산", "서울시 신림동 1688-12", "http://tong.visitkorea.or.kr/cms/resource/30/1857230_image2_1.jpg");
        String subject = "관악산 같이 오르실 분 구해요.";
        String detailInfo = "천천히 오르실 분을 원합니다.";
        int minRecruitment = 2;
        int maxRecruitment = 5;
        int minAge = 20;
        int maxAge = 30;
        LocalDate accompanyingDate = LocalDate.of(2022, 12, 25);
        GenderType preferGenderType = GenderType.MAN;

        CompanionRequest companionRequest =
                new CompanionRequest(userA.getId(), mountain, subject, detailInfo, accompanyingDate, minRecruitment, maxRecruitment, 0, minAge, maxAge, preferGenderType);

        List<Companion> saved = companionRepository.saveAll(List.of(Companion.of(userA, companionRequest), Companion.of(userA, companionRequest), Companion.of(userA, companionRequest)));
        List<Long> companionIds = saved.stream().map(c -> c.getId()).collect(Collectors.toList());

        List<Promise> promises = promiseRepository.saveAll(companionIds.stream()
                .map(c -> Promise.of(new PromiseRequest(userB.getId(), c, roomId), userA.getId()))
                .collect(Collectors.toList()));

        promises.stream().forEach(p -> p.setSecondPromiseUserId(userB.getId()));

        //when
        List<Companion> companions = promiseService.getPromisesById(userA.getId());

        //then
        assertEquals(companions.size(), promises.size());
        assertEquals(companions.get(0).getId(), promises.get(0).getCompanionId());
    }

    @Test
    @DisplayName("existsPromise는 userId와 companionId를 받아 Promise 유무를 반환한다.")
    public void testExistsPromisesSuccess() throws Exception {
        //given
        User user = createUser();
        Long oppositeUserId = 1L;
        Long companionId = 0L;
        Room room = createRoom();
        Long roomId = room.getId();
        PromiseRequest promiseRequest = new PromiseRequest(oppositeUserId, companionId, roomId);

        promiseService.save(promiseRequest, user);

        //when
        boolean existsPromise = promiseService.existsPromise(user.getId(), roomId);

        //then
        assertTrue(existsPromise);
    }

    private Promise createPromise(Long userId, Long oppositeUserId, Long companionId, Long roomId) {
        PromiseRequest promiseRequest = new PromiseRequest(userId, companionId, roomId);
        Promise promise = Promise.of(promiseRequest, oppositeUserId);

        return promiseRepository.save(promise);
    }

    private User createUser() {
        User user = User.builder()
                .email("email@email.com")
                .gender(GenderType.MAN)
                .birth(LocalDate.of(2020, 1, 1))
                .nickname("nickname")
                .build();
        return userRepository.save(user);
    }

    private Room createRoom() {
        User user = User.builder()
                .email("email@email.com")
                .gender(GenderType.MAN)
                .birth(LocalDate.of(2020, 1, 1))
                .nickname("nickname")
                .build();

        User opposite = User.builder()
                .email("email2@email.com")
                .gender(GenderType.MAN)
                .birth(LocalDate.of(2020, 1, 1))
                .nickname("nickname2")
                .build();

        List<User> users = userRepository.saveAll(List.of(user, opposite));

        Companion companion = Companion.builder()
                .user(users.get(0))
                .mountain(new Mountain("", "", ""))
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

        Companion save = companionRepository.save(companion);


        Room room = Room.builder()
                .companion(save)
                .companionContacter(users.get(1))
                .companionWriter(companion.getUser())
                .contacterRead(true)
                .build();

        return chatRepository.save(room);
    }
}