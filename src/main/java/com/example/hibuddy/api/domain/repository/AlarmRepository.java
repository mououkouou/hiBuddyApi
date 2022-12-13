package com.example.hibuddy.api.domain.repository;

import com.example.hibuddy.api.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    Optional<List<Alarm>> findAllByUserId(Long userId);
}
