package com.example.hibuddy.api.interfaces.response;

import com.example.hibuddy.api.domain.User;
import com.example.hibuddy.api.domain.support.GenderType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileView {
    private Long id;
    private String nickname;
    private GenderType gender;
    private int hikingLevel;
    private String profilePictureUrl;
    private LocalDate birth;

    public static ProfileView of(User user) {
        return ProfileView.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .gender(user.getGender())
                .hikingLevel(user.getHikingLevel())
                .profilePictureUrl(user.getProfilePictureUrl())
                .birth(user.getBirth())
                .build();
    }
}
