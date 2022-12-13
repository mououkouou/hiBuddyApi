package com.example.hibuddy.api.configuration.jwt;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Objects;

@Getter
public class CustomUserDetail extends User {

    private final com.example.hibuddy.api.domain.User user;

    public CustomUserDetail(com.example.hibuddy.api.domain.User user) {
        super(user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority("USER")));
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomUserDetail)) return false;
        if (!super.equals(o)) return false;
        CustomUserDetail customUserDetail = (CustomUserDetail) o;
        return Objects.equals(getUser(), customUserDetail.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUser());
    }
}
