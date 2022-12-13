package com.example.hibuddy.api.application;

import com.example.hibuddy.api.configuration.jwt.TokenProvider;
import com.example.hibuddy.api.interfaces.support.Token;
import com.example.hibuddy.api.domain.User;
import com.example.hibuddy.api.exception.MessageKey;
import com.example.hibuddy.api.exception.UnMatchUserInfoException;
import com.example.hibuddy.api.interfaces.request.AuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public Token login(final AuthRequest authRequest) {
        User user = userService.getUserByEmail(authRequest.getEmail());
        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new UnMatchUserInfoException(MessageKey.WRONG_PASSWORD);
        }

        return Token.of(tokenProvider.createAccessToken(user.getEmail()),
                authRequest.isRememberMe() ? tokenProvider.createRefreshToken(user.getEmail()) : null);
    }

    @Transactional
    public Token reIssue(final String email, final String refreshToken) {
        User user = userService.getUserByEmail(email);
        tokenProvider.checkRefreshToken(user.getEmail(), refreshToken);
        return Token.of(tokenProvider.createAccessToken(user.getEmail()), refreshToken);
    }

    @Transactional
    public void logout(String email, String accessToken) {
        tokenProvider.logout(email, accessToken);
    }
}
