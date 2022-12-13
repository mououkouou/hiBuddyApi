package com.example.hibuddy.api.interfaces.response;

import com.example.hibuddy.api.domain.FamousMountain;
import com.example.hibuddy.api.domain.support.RegionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class FamousMountainView {
    private Long id;
    private String name;
    private RegionType regionType;
    private String regionName;
    private Double height;
    private String description;
    private String url;

    public static FamousMountainView of(FamousMountain famousMountain) {
        return FamousMountainView.builder()
                .id(famousMountain.getId())
                .name(famousMountain.getName())
                .regionType(famousMountain.getRegion().getRegionType())
                .regionName(famousMountain.getRegion().getRegionType().getName())
                .height(famousMountain.getHeight())
                .description(famousMountain.getDescription())
                .url(famousMountain.getUrl())
                .build();
    }
}
