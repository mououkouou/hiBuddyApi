package com.example.hibuddy.api.interfaces.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {
    private Long roomId;
    private String content;
}
