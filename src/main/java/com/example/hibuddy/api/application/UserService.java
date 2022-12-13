package com.example.hibuddy.api.application;

import com.example.hibuddy.api.common.S3Uploader;
import com.example.hibuddy.api.domain.User;
import com.example.hibuddy.api.domain.Withdrawal;
import com.example.hibuddy.api.domain.repository.UserRepository;
import com.example.hibuddy.api.domain.repository.WithdrawalRepository;
import com.example.hibuddy.api.exception.UserNotFoundException;
import com.example.hibuddy.api.exception.WithdrawalException;
import com.example.hibuddy.api.interfaces.request.ModifyUserRequest;
import com.example.hibuddy.api.interfaces.request.ProfileImageRequest;
import com.example.hibuddy.api.interfaces.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final WithdrawalRepository withdrawalRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3Uploader s3Uploader;

    public User getUserById(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public User getUserByEmail(final String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    public User validEmail(final String email) {
        Optional<Withdrawal> withdrawal = withdrawalRepository.findByEmail(email);
        withdrawal.ifPresent(w -> {
            if (ChronoUnit.DAYS.between(w.getCreatedAt(), LocalDateTime.now()) < 10) {
                throw new WithdrawalException();
            }
        });

        return getUserByEmail(email);

    }

    public User getUserByNickname(final String nickname) {
        return userRepository.findByNickname(nickname)
                .orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public User register(final UserRequest request) {
        return userRepository.save(User.of(request, passwordEncoder.encode(request.getPassword())));
    }

    @Transactional
    public User modify(final Long id, final ModifyUserRequest request) {
        User user = getUserById(id);
        user.modify(request, request.getProfilePictureUrl() == null ? passwordEncoder.encode(request.getPassword()) : null);
        return user;
    }

    @Transactional
    public void delete(final Long id, final String email) {
        userRepository.deleteById(id);
        withdrawalRepository.save(Withdrawal.builder().email(email).build());
    }

    @Transactional
    public String upsertUserProfile(ProfileImageRequest request, Long userId) throws IOException {
        User user = getUserById(userId);
        String image = s3Uploader.upload(request.getImage(), "static");

        user.modify(image);

        return image;
    }
}
