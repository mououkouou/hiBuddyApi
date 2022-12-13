package com.example.hibuddy.api.interfaces.response;

import com.example.hibuddy.api.domain.support.KoreaTour;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MountainView {
    private String name;

    private String address;

    private String photoUrl;

    public static MountainView of(KoreaTour koreaTourResponse) {
        return MountainView.builder()
                .name(koreaTourResponse.getTitle())
                .address(koreaTourResponse.getAddr1())
                .photoUrl(koreaTourResponse.getFirstimage())
                .build();
    }
}
