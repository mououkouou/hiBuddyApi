package com.example.hibuddy.api.interfaces.response;

import com.example.hibuddy.api.domain.Promise;
import com.example.hibuddy.api.domain.support.PromiseStatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromiseResponse {
    private String userNickname;
    private PromiseStatusType promiseStatusType;

    public static PromiseResponse of(Promise promise, String nickname) {
        return PromiseResponse.builder()
                .userNickname(nickname)
                .promiseStatusType(promise.getPromiseStatusType())
                .build();
    }
}
