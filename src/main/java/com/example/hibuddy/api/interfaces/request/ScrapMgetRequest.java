package com.example.hibuddy.api.interfaces.request;

import com.example.hibuddy.api.domain.support.ScrapResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScrapMgetRequest {
    private List<Long> resourceIds;
    private ScrapResource resourceType;
}
