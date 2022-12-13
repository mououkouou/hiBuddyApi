package com.example.hibuddy.api.interfaces;

import com.example.hibuddy.api.application.AuthService;
import com.example.hibuddy.api.configuration.jwt.TokenProvider;
import com.example.hibuddy.api.interfaces.support.AuthUser;
import com.example.hibuddy.api.domain.User;
import com.example.hibuddy.api.interfaces.request.AuthRequest;
import com.example.hibuddy.api.interfaces.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        if (authRequest.getRefreshToken() != null) {
            String email = tokenProvider.getUserEmail(authRequest.getRefreshToken());
            return new ResponseEntity<>(AuthResponse.of(authService.reIssue(email, authRequest.getRefreshToken())), HttpStatus.OK);
        }
        return new ResponseEntity<>(AuthResponse.of(authService.login(authRequest)), HttpStatus.OK);
    }

    @DeleteMapping("/logout")
    public ResponseEntity logout(@AuthUser User user, HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization").substring(7);
        authService.logout(user.getEmail(), accessToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
