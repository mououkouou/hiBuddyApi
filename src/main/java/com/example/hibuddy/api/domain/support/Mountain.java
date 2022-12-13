package com.example.hibuddy.api.domain.support;

import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Mountain {
    private String name;

    private String address;

    private String photoUrl;
}
