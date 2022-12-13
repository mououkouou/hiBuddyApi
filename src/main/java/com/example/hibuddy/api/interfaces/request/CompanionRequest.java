package com.example.hibuddy.api.interfaces.request;

import com.example.hibuddy.api.domain.support.Mountain;
import com.example.hibuddy.api.domain.support.GenderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Valid
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompanionRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Mountain mountain;
    @NotNull
    private String subject;
    @NotNull
    private String detailInfo;
    @NotNull
    private LocalDate accompanyingDate;

    private int minRecruitment;
    private int maxRecruitment;

    @NotNull
    private int fixedNumber;

    private int minAge;
    private int maxAge;

    private GenderType preferGenderType;
}
