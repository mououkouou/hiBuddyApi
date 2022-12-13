package com.example.hibuddy.api.domain;

import com.example.hibuddy.api.domain.support.RegionType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class RegionBoard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private MountainBoard mountainBoard;

    @Enumerated(value = EnumType.STRING)
    private RegionType regionType;

    private Double height;

    private int count;


    public static RegionBoard initialize(FamousMountain famousMountain, MountainBoard mountainBoard) {
        return RegionBoard.builder()
                .mountainBoard(mountainBoard)
                .regionType(famousMountain.getRegion()
                        .getRegionType())
                .height(famousMountain.getHeight())
                .count(0).build();
    }

    public RegionBoard update() {
        this.count += 1;
        return this;
    }
}
