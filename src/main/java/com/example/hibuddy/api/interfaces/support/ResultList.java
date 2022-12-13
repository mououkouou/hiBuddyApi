package com.example.hibuddy.api.interfaces.support;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class ResultList<T> {
    private T data;
}
