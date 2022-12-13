package com.example.hibuddy.api.interfaces.response;

import com.example.hibuddy.api.domain.Companion;
import com.example.hibuddy.api.domain.support.Mountain;
import com.example.hibuddy.api.domain.support.GenderType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanionView {
    private Long id;
    private ProfileView profileView;
    private String subject;
    private String detailInfo;
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    private LocalDate accompanyingDate;
    private int minRecruitment;
    private int maxRecruitment;
    private int fixedNumber;
    private int minAge;
    private int maxAge;
    private GenderType preferGenderType;
    private int scrapCount;

    private Mountain mountain;

    public static CompanionView of(Companion companion) {
        return CompanionView.builder()
                .id(companion.getId())
                .profileView(ProfileView.of(companion.getUser()))
                .subject(companion.getSubject())
                .detailInfo(companion.getDetail())
                .accompanyingDate(companion.getAccompanyingDate())
                .minRecruitment(companion.getMinRecruitment())
                .maxRecruitment(companion.getMaxRecruitment())
                .minAge(companion.getMinAge())
                .maxAge(companion.getMaxAge())
                .preferGenderType(companion.getPreferGenderType())
                .fixedNumber(companion.getFixedNumber())
                .mountain(companion.getMountain())
                .build();
    }
}
