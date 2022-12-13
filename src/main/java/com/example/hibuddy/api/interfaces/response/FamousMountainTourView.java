package com.example.hibuddy.api.interfaces.response;

import com.example.hibuddy.api.domain.FamousMountain;
import com.example.hibuddy.api.domain.support.KoreaTour;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class FamousMountainTourView {
    private String name;

    private String photoUrl;

    private String address;

    private String phoneNumber;

    private String content;

    private String restDate;

    private String creditCard;

    private String parking;

    private String baby;

    private String time;

    private String pet;

    public static FamousMountainTourView of(FamousMountain famousMountain, KoreaTour info, KoreaTour intro) {
        return FamousMountainTourView.builder()
                .name(famousMountain.getName())
                .photoUrl(famousMountain.getUrl())
                .address(info.getAddr1())
                .content(info.getOverview())
                .phoneNumber(intro.getInfocenter())
                .restDate(intro.getRestdate())
                .creditCard(intro.getChkcreditcard())
                .parking(intro.getParking())
                .baby(intro.getChkbabycarriage())
                .time(intro.getUsetime())
                .pet(intro.getChkpet())
                .build();
    }
}
