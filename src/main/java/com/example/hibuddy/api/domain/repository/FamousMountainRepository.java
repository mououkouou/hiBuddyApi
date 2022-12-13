package com.example.hibuddy.api.domain.repository;

import com.example.hibuddy.api.domain.FamousMountain;
import com.example.hibuddy.api.domain.support.RegionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FamousMountainRepository extends JpaRepository<FamousMountain, Long> {
    List<FamousMountain> findByIdIn(List<Long> ids);
    int countAllByRegion_RegionType(RegionType regionType);
}
