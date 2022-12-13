package com.example.hibuddy.api.interfaces.response;

import com.example.hibuddy.api.domain.support.GenderType;
import com.example.hibuddy.api.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserView {
    private Long id;
    private String email;
    private String nickname;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, locale = "Asia/Seoul")
    private LocalDate birth;
    private GenderType gender;
    private int hikingLevel;
    private String profilePictureUrl;
    private String createAt;
    private int backgroundNum;

    public static UserView of(User user) {
        return UserView.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .birth(user.getBirth())
                .gender(user.getGender())
                .hikingLevel(user.getHikingLevel())
                .profilePictureUrl(user.getProfilePictureUrl())
                .createAt(user.getCreatedAt().getYear() + "년 " + user.getCreatedAt().getMonthValue() + "월 " + user.getCreatedAt().getDayOfMonth() + "일")
                .backgroundNum(user.getBackgroundNum())
                .build();

    }
}
