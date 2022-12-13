package com.example.hibuddy.api.interfaces.response;

import com.example.hibuddy.api.domain.Scrap;
import com.example.hibuddy.api.domain.support.ScrapResource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScrapListView {
    private Long id;
    private Long resourceId;
    private boolean scraped;
    private ScrapResource resourceType;

    public static List<ScrapListView> buildScrapListView(List<Scrap> scraps,
                                                         List<Long> resourceIds,
                                                         ScrapResource resourceType) {
        HashMap<Long, Scrap> scrapResourceIdsMap = new HashMap<>();
        scraps.forEach(scrap -> scrapResourceIdsMap.put(scrap.getResourceId(), scrap));

        return resourceIds.stream()
                .map(resourceId -> scrapResourceIdsMap.containsKey(resourceId)
                        ? ScrapListView.of(scrapResourceIdsMap.get(resourceId))
                        : ScrapListView.of(resourceId, resourceType))
                .toList();
    }

    private static ScrapListView of(Scrap scrap) {
        return ScrapListView.builder()
                .id(scrap.getId())
                .resourceId(scrap.getResourceId())
                .scraped(true)
                .resourceType(scrap.getResourceType())
                .build();
    }

    private static ScrapListView of(Long resourceId,
                                    ScrapResource resourceType) {
        return ScrapListView.builder()
                .id(null)
                .resourceId(resourceId)
                .scraped(false)
                .resourceType(resourceType)
                .build();
    }
}
