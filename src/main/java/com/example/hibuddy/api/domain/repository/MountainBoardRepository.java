package com.example.hibuddy.api.domain.repository;

import com.example.hibuddy.api.domain.MountainBoard;
import com.example.hibuddy.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MountainBoardRepository extends JpaRepository<MountainBoard, Long> {
    boolean existsByUser(User user);
    Optional<MountainBoard> findByUser(User user);
    Long countAllByTotalHikingCountGreaterThan(Long count);
}
