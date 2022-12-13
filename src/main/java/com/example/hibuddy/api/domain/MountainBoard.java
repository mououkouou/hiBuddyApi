package com.example.hibuddy.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class MountainBoard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private Long totalHikingCount;

    private Double totalHeightCount;

    public static MountainBoard initialize(User user) {
        return MountainBoard.builder()
                .user(user)
                .totalHeightCount((double) 0)
                .totalHikingCount(0L)
                .build();
    }

    public MountainBoard update(FamousMountain famousMountain) {
        this.totalHeightCount += famousMountain.getHeight();
        this.totalHikingCount += 1;

        return this;
    }
}
