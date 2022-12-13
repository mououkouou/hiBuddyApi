package com.example.hibuddy.api.domain;

import com.example.hibuddy.api.domain.support.GenderType;
import com.example.hibuddy.api.domain.support.Mountain;
import com.example.hibuddy.api.interfaces.request.CompanionRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "companion")
public class Companion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Embedded
    private Mountain mountain;

    private String subject;

    private String detail;

    private LocalDate accompanyingDate;

    private int minRecruitment;
    private int maxRecruitment;

    private int fixedNumber;

    private int minAge;
    private int maxAge;

    private GenderType preferGenderType;

    public static Companion of(User user, CompanionRequest request) {
        return Companion.builder()
                .user(user)
                .mountain(request.getMountain())
                .subject(request.getSubject())
                .detail(request.getDetailInfo())
                .accompanyingDate(request.getAccompanyingDate())
                .minRecruitment(request.getMinRecruitment())
                .maxRecruitment(request.getMaxRecruitment())
                .fixedNumber(0)
                .minAge(request.getMinAge())
                .maxAge(request.getMaxAge())
                .preferGenderType(request.getPreferGenderType())
                .build();
    }

    public void addFixedNumber() {
        this.fixedNumber = this.fixedNumber + 1;
    }
}
