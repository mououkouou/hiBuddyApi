package com.example.hibuddy.api.domain.repository.support;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class CountDto {
    private Long regionCount;
    private Double totalHeightCount;
    private Long totalHikingCount;
}
