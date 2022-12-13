package com.example.hibuddy.api.configuration.jwt;

import com.example.hibuddy.api.application.UserService;
import com.example.hibuddy.api.domain.User;
import com.example.hibuddy.api.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
        User user = userService.getUserByEmail(email);
        return new CustomUserDetail(user);
    }
}
