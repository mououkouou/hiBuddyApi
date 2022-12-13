package com.example.hibuddy.api.interfaces.response;

import com.example.hibuddy.api.interfaces.support.Token;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String accessToken;
    private String refreshToken;

    public static AuthResponse of(Token token) {
        if (token.getRefreshToken() != null) {
            return AuthResponse.builder()
                    .accessToken(token.getAccessToken())
                    .refreshToken(token.getRefreshToken())
                    .build();
        } else {
            return AuthResponse.builder()
                    .accessToken(token.getAccessToken())
                    .build();
        }
    }
}
