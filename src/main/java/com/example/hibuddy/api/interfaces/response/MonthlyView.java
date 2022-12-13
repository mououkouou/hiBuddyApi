package com.example.hibuddy.api.interfaces.response;

import com.example.hibuddy.api.domain.MonthlyRegionBoard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonthlyView {
    private String name;
    private int count;

    public static MonthlyView of(MonthlyRegionBoard board) {
        return MonthlyView.builder()
                .name(board.getRegionBoard().getRegionType().getName())
                .count(board.getCount())
                .build();
    }
}
