package com.example.hibuddy.api.domain.repository;

import com.example.hibuddy.api.domain.FamousMountain;
import com.example.hibuddy.api.domain.Region;
import com.example.hibuddy.api.domain.User;
import com.example.hibuddy.api.domain.VisitedMountain;
import com.example.hibuddy.api.domain.support.RegionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitedMountainRepository extends JpaRepository<VisitedMountain, Long> {
    boolean existsByUserAndFamousMountain(User user, FamousMountain famousMountain);
    List<VisitedMountain> findAllByUser(User user);
    List<VisitedMountain> findAllByUserAndFamousMountain_Region_RegionType(User user, RegionType type);
}
