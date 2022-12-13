package com.example.hibuddy.api.interfaces.response;

import com.example.hibuddy.api.application.ScrapService;
import com.example.hibuddy.api.domain.Scrap;
import com.example.hibuddy.api.domain.repository.ScrapRepository;
import com.example.hibuddy.api.domain.support.ScrapResource;
import com.example.hibuddy.api.interfaces.request.ScrapMgetRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class ScrapListViewTest {

    @Autowired
    ScrapService scrapService;

    @Autowired
    ScrapRepository scrapRepository;

    @BeforeEach
    void setUp() {
        scrapRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        scrapRepository.deleteAll();
    }

    @Test
    @DisplayName("buildScrapListView는 resourceId가 scrap되지 않은 경우 scraped false를 포함합니다.")
    void testBuildScrapListView() {
        // given
        List<Long> scrapedResourceIds = List.of(0L, 1L);
        Long notScrapedResourceId = 2L;
        ScrapResource resourceType = ScrapResource.COMPANION;


        scrapRepository.saveAll(scrapedResourceIds.stream()
                .map(id -> Scrap.builder()
                        .userId(0L)
                        .resourceId(id)
                        .resourceType(resourceType)
                        .build())
                .toList()
        );

        List<Scrap> scraps = scrapService.getScrapsByResourceIdsAndType(new ScrapMgetRequest(scrapedResourceIds, resourceType));

        List<Long> resourceIds = new ArrayList<Long>(scrapedResourceIds);
        resourceIds.add(notScrapedResourceId);

        // when, then
        List<ScrapListView> scrapListViews = ScrapListView.buildScrapListView(scraps, resourceIds, resourceType);

        HashMap<Long, Boolean> expected = new HashMap<>();
        resourceIds.stream()
                .map(id -> scrapedResourceIds.contains(id) ? expected.put(id, true) : expected.put(id, false));

        HashMap<Long, Boolean> actual = new HashMap<>();

        scrapListViews.stream().map(view -> actual.put(view.getResourceId(), view.isScraped()));

        assertEquals(expected, actual);
    }
}