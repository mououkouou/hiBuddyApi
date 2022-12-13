package com.example.hibuddy.api.interfaces.response;

import com.example.hibuddy.api.domain.Companion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfilePromiseView {
    private Long companionId;
    private String subject;
    private String mountainPhotoUrl;
    private String mountainName;
    private String mountainAddress;
    private String companionWriterNickname;
    private String companionDate;

    public static ProfilePromiseView of (Companion companion) {
        return ProfilePromiseView.builder()
                .companionId(companion.getId())
                .subject(companion.getSubject())
                .mountainName(companion.getMountain().getName())
                .mountainAddress(companion.getMountain().getAddress())
                .mountainPhotoUrl(companion.getMountain().getPhotoUrl())
                .companionWriterNickname(companion.getUser().getNickname())
                .companionDate(companion.getAccompanyingDate().toString().replaceAll("-", "."))
                .build();
    }
}
