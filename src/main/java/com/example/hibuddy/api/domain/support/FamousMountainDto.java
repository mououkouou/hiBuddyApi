package com.example.hibuddy.api.domain.support;

import com.example.hibuddy.api.domain.FamousMountain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FamousMountainDto {

    private Long id;

    private RegionType regionType;

    private Double height;

    public static FamousMountainDto of(FamousMountain famousMountain) {
        return FamousMountainDto.builder()
                .id(famousMountain.getId())
                //.regionType(famousMountain.getRegion().getRegionType())
                .height(famousMountain.getHeight())
                .build();
    }
}
