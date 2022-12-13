package com.example.hibuddy.api.application;

import com.example.hibuddy.api.domain.Scrap;
import com.example.hibuddy.api.domain.repository.ScrapRepository;
import com.example.hibuddy.api.domain.support.ScrapResource;
import com.example.hibuddy.api.exception.DuplicatedScrapException;
import com.example.hibuddy.api.interfaces.request.ScrapMgetRequest;
import com.example.hibuddy.api.interfaces.request.ScrapRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class ScrapServiceTest {

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
    @DisplayName("save는 ScrapRequest를 받아 Scrap 객체를 저장합니다.")
    void testSaveSuccess() {
        // given
        Long userId = 0L;
        Long resourceId = 0L;
        ScrapResource resourceType = ScrapResource.COMPANION;
        ScrapRequest request = new ScrapRequest(userId, resourceId, resourceType);

        // when
        Scrap saved = scrapService.save(request);
        // then
        Scrap scrap = scrapRepository.findById(saved.getId())
                .get();

        assertEquals(saved.getId(), scrap.getId());
        assertEquals(userId, scrap.getUserId());
        assertEquals(resourceType, scrap.getResourceType());
        assertEquals(resourceId, scrap.getResourceId());
    }

    @Test
    @DisplayName("save는 resource와 userId가 이미 존재하는 경우 DuplicatedScrapException를 발생시킵니다.")
    void testSaveFail() {
        // given
        Long userId = 0L;
        Long resourceId = 0L;
        ScrapResource resourceType = ScrapResource.COMPANION;

        scrapRepository.save(Scrap.builder()
                .userId(userId)
                .resourceId(resourceId)
                .resourceType(resourceType)
                .build());

        // then
        assertThrows(DuplicatedScrapException.class, () -> {
            scrapService.save(new ScrapRequest(userId, resourceId, resourceType));
        });
    }

    @Test
    @DisplayName("getScrapsByResourceIdsAndType는 resourceIds와 type을 통해 scrap들을 리턴합니다.")
    void testMgetSuccess() {
        // given
        ScrapResource resourceType = ScrapResource.COMPANION;

        List<Long> resourceIds = List.of(0L, 1L, 2L);

        scrapRepository.saveAll(resourceIds.stream()
                .map(id -> Scrap.builder()
                        .userId(0L)
                        .resourceId(id)
                        .resourceType(resourceType)
                        .build())
                .toList()
        );

        ScrapMgetRequest request = new ScrapMgetRequest(resourceIds, resourceType);

        // when
        List<Scrap> scraps = scrapService.getScrapsByResourceIdsAndType(request);

        // then
        assertEquals(resourceIds, scraps.stream()
                .map(Scrap::getResourceId)
                .toList());
    }
}
