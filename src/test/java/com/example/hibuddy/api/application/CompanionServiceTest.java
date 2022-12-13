package com.example.hibuddy.api.application;

import com.example.hibuddy.api.domain.Companion;
import com.example.hibuddy.api.domain.support.Mountain;
import com.example.hibuddy.api.domain.User;
import com.example.hibuddy.api.domain.repository.CompanionRepository;
import com.example.hibuddy.api.domain.repository.UserRepository;
import com.example.hibuddy.api.domain.support.GenderType;
import com.example.hibuddy.api.interfaces.request.CompanionRequest;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class CompanionServiceTest {

    @Autowired
    CompanionService companionService;

    @Autowired
    CompanionRepository companionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        companionRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        companionRepository.deleteAll();
    }

    @Test
    @DisplayName("save는 CompanionRequest를 받아 Companion을 저장합니다.")
    public void 동행저장() throws Exception {
        //given
        User user = createUser();
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
                new CompanionRequest(user.getId(), mountain, subject, detailInfo, accompanyingDate, minRecruitment, maxRecruitment, 0, minAge, maxAge, preferGenderType);
        //when
        Companion companion = companionService.save(companionRequest);

        //then
        assertEquals(user.getNickname(), companion.getUser().getNickname());
        assertEquals(mountain, companion.getMountain());
        assertEquals(subject, companion.getSubject());
        assertEquals(detailInfo, companion.getDetail());
        assertEquals(minRecruitment, companion.getMinRecruitment());
        assertEquals(maxRecruitment, companion.getMaxRecruitment());
        assertEquals(minAge, companion.getMinAge());
        assertEquals(maxAge, companion.getMaxAge());
        assertEquals(accompanyingDate, companion.getAccompanyingDate());
        assertEquals(preferGenderType, companion.getPreferGenderType());
    }

    @Test
    public void 동행상세() throws Exception {
        //given
        Companion companion = createCompanion();

        //when
        Companion getCompanion = companionService.getCompanionById(companion.getId());

        //then
        assertEquals("dummy", getCompanion.getUser().getNickname());
        assertEquals("관악산", getCompanion.getMountain().getName());
        assertEquals("관악산 같이 오르실 분 구해요.", getCompanion.getSubject());
        assertEquals("관악산 같이 오르실 분 구해요.", getCompanion.getDetail());
        assertEquals(2, getCompanion.getMinRecruitment());
        assertEquals(5, getCompanion.getMaxRecruitment());
        assertEquals(20, getCompanion.getMinAge());
        assertEquals(30, getCompanion.getMaxAge());
        assertEquals(LocalDate.now(), getCompanion.getAccompanyingDate());
        assertEquals(null, getCompanion.getPreferGenderType());
    }

    @Test
    public void 유저삭제시_동행삭제처리() throws Exception {
        //given
        Companion companion = createCompanion();
        User user = companion.getUser();

        userRepository.delete(user);

        //when then
         Optional<Companion> getCompanion = companionRepository.findById(companion.getId());

        //then
        assertTrue(getCompanion.isEmpty());
    }

    private User createUser() {
        User user = User.builder()
                .email("dummy@email.com")
                .gender(GenderType.MAN)
                .birth(LocalDate.of(2020, 1, 1))
                .password(passwordEncoder.encode("dummy"))
                .nickname("dummy")
                .build();
        return userRepository.save(user);
    }

    private Companion createCompanion() {
        Mountain mountain = new Mountain("관악산", "서울시 신림동 1688-12", "http://tong.visitkorea.or.kr/cms/resource/30/1857230_image2_1.jpg");
        User user = createUser();
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
}