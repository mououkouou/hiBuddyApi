package com.example.hibuddy.api.application;

import com.example.hibuddy.api.domain.support.KoreaTour;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class KoreaTourServiceTest {
    @Autowired
    KoreaTourService koreaTourService;

    @Test
    @DisplayName("getMountains는 검색어를 받아 산 목록을 보여줍니다.")
    public void 산_검색_목록() throws Exception {
        //given
        String keyword = "관악산";

        //when
        List<KoreaTour> mountains = koreaTourService.getMountains(keyword).block();

        //then
        assertEquals(mountains.size(), 4);
        assertEquals(mountains.get(0).getTitle(), "관악산");
        assertEquals(mountains.get(0).getAddr1(), "서울특별시 관악구 관악로");
        assertEquals(mountains.get(0).getFirstimage(), "http://tong.visitkorea.or.kr/cms/resource/30/1857230_image2_1.jpg");
    }
}