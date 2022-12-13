package com.example.hibuddy.api.interfaces.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileImageView {
    private String profileImage;

    public static ProfileImageView of(String image) {
        return new ProfileImageView(image);
    }
}
