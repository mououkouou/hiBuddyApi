package com.example.hibuddy.api.domain.repository;

import com.example.hibuddy.api.domain.FamousMountain;
import com.example.hibuddy.api.domain.MountainBoard;
import com.example.hibuddy.api.domain.RegionBoard;
import com.example.hibuddy.api.domain.support.RegionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegionBoardRepository extends JpaRepository<RegionBoard, Long> {
    Optional<RegionBoard> findByMountainBoardAndRegionType(MountainBoard mountainBoard, RegionType regionType);
    RegionBoard findTopByMountainBoardOrderByCountDesc(MountainBoard mountainBoard);
}
