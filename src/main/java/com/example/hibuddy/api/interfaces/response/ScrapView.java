package com.example.hibuddy.api.interfaces.response;

import com.example.hibuddy.api.domain.Scrap;
import com.example.hibuddy.api.domain.support.ScrapResource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScrapView {
    private Long id;
    private Long userId;
    private Long resourceId;
    private ScrapResource resourceType;

    public static ScrapView of(Scrap scrap) {
        return ScrapView.builder()
                .id(scrap.getId())
                .userId(scrap.getUserId())
                .resourceId(scrap.getResourceId())
                .resourceType(scrap.getResourceType())
                .build();
    }
}
