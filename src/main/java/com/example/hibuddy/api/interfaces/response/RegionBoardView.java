package com.example.hibuddy.api.interfaces.response;

import com.example.hibuddy.api.domain.RegionBoard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegionBoardView {
    private String name;
    private int count;

    public static RegionBoardView of(RegionBoard regionBoard) {
        return RegionBoardView.builder()
                .count(regionBoard.getCount())
                .name(regionBoard.getRegionType().getName())
                .build();
    }
}
