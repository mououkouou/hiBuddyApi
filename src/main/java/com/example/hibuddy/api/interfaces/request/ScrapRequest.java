package com.example.hibuddy.api.interfaces.request;

import com.example.hibuddy.api.domain.support.ScrapResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScrapRequest {
    private Long userId;
    private Long resourceId;
    private ScrapResource resourceType;
}
