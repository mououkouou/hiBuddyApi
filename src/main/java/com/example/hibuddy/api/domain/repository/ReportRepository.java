package com.example.hibuddy.api.domain.repository;

import com.example.hibuddy.api.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
