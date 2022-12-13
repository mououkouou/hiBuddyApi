package com.example.hibuddy.api.domain.support;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RegionType {
    SEOUL("서울" ),
    KYUNG_KI("경기"),
    SE_KK("서울/경기"),
    KANG_WON("강원도"),
    CHUNG_BOOK("충청북도"),
    CHUNG_NAM("충청남도"),
    KYUNG_BOOK("경상북도"),
    KYUNG_NAM("경상남도"),
    JEON_BOOK("전라북도"),
    JEON_NAM("전라남도"),
    JEJU("제주"),
    ;

    private String name;
}
