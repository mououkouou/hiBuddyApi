package com.example.hibuddy.api.interfaces.request;

import com.example.hibuddy.api.domain.support.GenderType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Valid
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotNull
    private String email;

    @NotNull
    private String nickname;

    @NotNull
    private String password;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "Asia/Seoul")
    private LocalDate birth;

    @NotNull
    private GenderType gender;

    private int hikingLevel;
}
