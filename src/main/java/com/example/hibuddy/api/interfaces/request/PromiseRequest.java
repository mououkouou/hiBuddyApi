package com.example.hibuddy.api.interfaces.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PromiseRequest {
    private Long oppositeUserId;
    private Long companionId;
    private Long roomId;
}
