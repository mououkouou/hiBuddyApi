package com.example.hibuddy.api.interfaces.response;

import com.example.hibuddy.api.domain.VisitedMountain;
import com.example.hibuddy.api.domain.support.RegionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VisitedMountainView {
    private RegionType regionType;
    private int counts;
    private int totalCounts;

    public static VisitedMountainView of(List<VisitedMountain> mountains, RegionType regionType, int totalCounts) {
        return VisitedMountainView.builder()
                .regionType(regionType)
                .counts(mountains.size())
                .totalCounts(totalCounts)
                .build();
    }
}
