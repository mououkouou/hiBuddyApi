package com.example.hibuddy.api.application;

import com.example.hibuddy.api.domain.support.GenderType;
import com.example.hibuddy.api.domain.User;
import com.example.hibuddy.api.domain.repository.UserRepository;
import com.example.hibuddy.api.exception.UserNotFoundException;
import com.example.hibuddy.api.interfaces.request.ModifyUserRequest;
import com.example.hibuddy.api.interfaces.request.UserRequest;
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
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private final String DUMMY_EMAIL = "dummy@email.com";

    private User generateDummyUser() {
        return User.builder()
                .email(DUMMY_EMAIL)
                .gender(GenderType.MAN)
                .birth(LocalDate.of(2020, 1, 1))
                .password(passwordEncoder.encode("dummy"))
                .nickname("dummy")
                .build();
    }

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("getUserById는 id를 통해 User를 반환합니다.")
    void testGetByUserIdSuccess() {
        // given
        User dummyUser = userRepository.save(generateDummyUser());
        Long id = dummyUser.getId();

        // when
        User user = userService.getUserById(id);

        // then
        assertDoesNotThrow(UserNotFoundException::new);
        assertEquals(dummyUser.getId(), user.getId());
    }

    @Test
    @DisplayName("getUserById는 id에 해당하는 User가 없을 때 에러를 발생시킵니다.")
    void testGetByUserIdFail() {
        // given
        Long id = 0L;

        //when, then
        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(id);
        });
    }

    @Test
    @DisplayName("getUserByEmail은 email과 일치하는 User를 반환합니다.")
    void testGetByEmailSuccess() {
        // given
        User dummyUser = userRepository.save(generateDummyUser());

        // when
        User user = userService.getUserByEmail(DUMMY_EMAIL);

        // then
        assertEquals(dummyUser.getId(), user.getId());
    }

    @Test
    @DisplayName("getUserByEmail은 email과 일치하는 User가 없을 때 예외를 발생시킵니다.")
    void testGetByEmailFail() {
        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserByEmail("email");
        });
    }

    @Test
    @DisplayName("register는 UserRequest를 받아 User를 저장합니다.")
    void testRegisterSuccess() {
        // given
        String nickname = "nickname";
        String password = "password";
        String encodedPassword = passwordEncoder.encode(password);
        LocalDate birth = LocalDate.of(2020, 1, 1);
        GenderType gender = GenderType.MAN;
        int level = 0;

        UserRequest request = new UserRequest(DUMMY_EMAIL, nickname,
                encodedPassword,
                birth,
                gender,
                level);

        // when
        User savedUser = userService.register(request);
        User user = userService.getUserById(savedUser.getId());

        // then
        assertEquals(nickname, user.getNickname());
        assertNotEquals(password, user.getPassword());
        assertTrue(passwordEncoder.matches(password, encodedPassword));
        assertEquals(DUMMY_EMAIL, user.getEmail());
        assertEquals(gender, user.getGender());
        assertEquals(birth, user.getBirth());
        assertEquals(level, user.getHikingLevel());
    }

    @Test
    @DisplayName("getUserByNickname은 닉네임과 일치하는 유저를 반환합니다.")
    public void 닉네임유저찾기_성공() throws Exception {
        //given
        User dummyUser = userRepository.save(generateDummyUser());

        //when
        User user = userService.getUserByNickname("dummy");

        //then
        assertEquals(dummyUser.getNickname(), user.getNickname());
    }

    @Test
    @DisplayName("getUserByNickname은 닉네임과 일치하는 유저가 없을 시 예외를 발생시킵니다.")
    public void 닉네임유저찾기_실패() throws Exception {
        //given
        userRepository.save(generateDummyUser());

        //when then
        assertThrows(UserNotFoundException.class, () ->
                userService.getUserByNickname("wrongNickname")
        );
    }

    @Test
    @DisplayName("modify는 UserRequest에 비밀번호만 있을 시 비밀번호를 변경합니다.")
    public void 회원정보수정_비밀번호() throws Exception {
        //given
        User user = userRepository.save(generateDummyUser());

        String differentPass = "differentPass";
        ModifyUserRequest request = new ModifyUserRequest(differentPass, null, 0, null, null);

        //when
        User modifyUser = userService.modify(user.getId(), request);

        //then
        assertNotEquals(user.getPassword(), modifyUser.getPassword());
        assertTrue(passwordEncoder.matches(differentPass, modifyUser.getPassword()));
    }

    @Test
    @DisplayName("modify는 UserRequest에 프로필 이미지가 없을 시 닉네임, 성별 등의 필드를 변경합니다.")
    public void 회원정보수정_프로필() throws Exception {
        //given
        User user = userRepository.save(generateDummyUser());

        String nickname = "diffNickname";
        int hikingLevel = 2;
        GenderType gender = GenderType.WOMAN;

        String differentPass = "differentPass";
        ModifyUserRequest request = new ModifyUserRequest(differentPass, nickname, hikingLevel, gender, null);

        //when
        User modifyUser = userService.modify(user.getId(), request);

        //then
        assertEquals(nickname, modifyUser.getNickname());
        assertEquals(hikingLevel, modifyUser.getHikingLevel());
        assertEquals(gender, modifyUser.getGender());

        assertNotEquals(user.getNickname(), modifyUser.getNickname());
        assertNotEquals(user.getHikingLevel(), modifyUser.getHikingLevel());
        assertNotEquals(user.getGender(), modifyUser.getGender());
    }
}