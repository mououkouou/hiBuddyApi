package com.example.hibuddy.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VisitedMountain extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne
    @JoinColumn(name = "famous_mountain_id")
    private FamousMountain famousMountain;

    public static VisitedMountain of(User user, FamousMountain famousMountain) {
        return VisitedMountain.builder()
                .user(user)
                .famousMountain(famousMountain)
                .build();
    }
}
