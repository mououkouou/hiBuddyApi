package com.example.hibuddy.api.domain.support;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class AlarmObject {
    private Long objectId;

    private String objectProfileUrl;

    private Long roomId;
}
