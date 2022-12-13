package com.example.hibuddy.api.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MonthlyRegionBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ManyToOne
    private RegionBoard regionBoard;

    private String month;

    private int count;

    public static MonthlyRegionBoard initialize(RegionBoard regionBoard, Long userId) {
        return MonthlyRegionBoard.builder()
                .regionBoard(regionBoard)
                .userId(userId)
                .month(LocalDate.now().format(DateTimeFormatter.ISO_DATE).substring(0, 7))
                .count(0)
                .build();
    }

    public MonthlyRegionBoard update() {
        this.count += 1;
        return this;
    }
}
