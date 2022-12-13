package com.example.hibuddy.api.domain;

import com.example.hibuddy.api.interfaces.request.ModifyUserRequest;
import com.example.hibuddy.api.domain.support.GenderType;
import com.example.hibuddy.api.interfaces.request.UserRequest;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "user", indexes = {
        @Index(name = "email_index", columnList = "email"),
        @Index(name = "nickname_index", columnList = "nickname")})
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String nickname;

    private LocalDate birth;

    @Enumerated(value = EnumType.STRING)
    private GenderType gender;

    private int hikingLevel;

    private String profilePictureUrl;

    private int backgroundNum;

    // TODO: 좀 더 정확한 스펙이 나오면 추가
    // private String preferenceLocation;


    public static User of(UserRequest request,
                          String encodedPassword) {
        return User.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .nickname(request.getNickname())
                .birth(request.getBirth())
                .gender(request.getGender())
                .hikingLevel(request.getHikingLevel())
                .backgroundNum((int)(Math.random() * 5))
                .build();

    }

    /**
     * 회원정보 수정
     */
    public void modify(ModifyUserRequest request, String encodedPassword) {
        if (request.getProfilePictureUrl() != null) {
            this.profilePictureUrl = request.getProfilePictureUrl();
        } else if (request.getNickname() != null) {
            this.password = encodedPassword;
            this.nickname = request.getNickname();
            this.hikingLevel = request.getHikingLevel();
            this.gender = request.getGender();
        } else {
            this.password = encodedPassword;
        }
    }

    public void modify(String image) {
        this.profilePictureUrl = image;
    }
}
