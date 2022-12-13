package com.example.hibuddy.api.domain.repository;

import com.example.hibuddy.api.domain.MonthlyRegionBoard;
import com.example.hibuddy.api.domain.RegionBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MonthlyRegionBoardRepository extends JpaRepository<MonthlyRegionBoard, Long> {
    Optional<MonthlyRegionBoard> findByRegionBoardAndMonth(RegionBoard regionBoard, String month);
    List<MonthlyRegionBoard> findAllByUserIdAndMonthOrderByCountDesc(Long userId, String month);
}
