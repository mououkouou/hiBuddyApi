package com.example.hibuddy.api.domain;

import com.example.hibuddy.api.domain.support.RegionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "region")
public class Region {

    @Id
    private Long id;

    private String division;

    @Enumerated(value = EnumType.STRING)
    private RegionType regionType;

    // .. ?
}
