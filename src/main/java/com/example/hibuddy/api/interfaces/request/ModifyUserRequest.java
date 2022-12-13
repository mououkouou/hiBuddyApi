package com.example.hibuddy.api.interfaces.request;

import com.example.hibuddy.api.domain.support.GenderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ModifyUserRequest {

    private String password;

    private String nickname;

    private int hikingLevel;

    private GenderType gender;

    private String profilePictureUrl;

}
