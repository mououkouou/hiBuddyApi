package com.example.hibuddy.api.domain;

import com.example.hibuddy.api.domain.support.FamousMountainSource;
import com.example.hibuddy.api.interfaces.request.FamousMountainRequest;
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
@Table(name = "famous_mountain")
public class FamousMountain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    private Double height;

    private String url;
    private String description;

    private String contentTypeId;
    private String contentId;

    public static FamousMountain from(FamousMountainRequest request, Region region) {
        return FamousMountain.builder()
                .name(request.getName())
                .region(region)
                .height(request.getHeight())
                .url(request.getUrl())
                .description(request.getDescription())
                .build();
    }
}

