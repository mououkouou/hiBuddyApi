package com.example.hibuddy.api.interfaces.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FamousMountainRequest {
    private String name;
    private Long regionId;
    private Double height;
    private String url;
    private String description;
}
