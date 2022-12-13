package com.example.hibuddy.api.application.support;

import com.example.hibuddy.api.domain.FamousMountain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto {
    private FamousMountain winner;
    private FamousMountain loser;
}
