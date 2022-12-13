package com.example.hibuddy.api.domain.support;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class FamousMountainSource {
    private String url;
    private String description;
    // ...
}
