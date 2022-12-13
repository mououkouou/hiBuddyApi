package com.example.hibuddy.api.application;

import com.example.hibuddy.api.domain.Report;
import com.example.hibuddy.api.domain.repository.ReportRepository;
import com.example.hibuddy.api.interfaces.request.ReportRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;

    @Transactional
    public Report save(final ReportRequest reportRequest) {
        return reportRepository.save(Report.of(reportRequest));
    }
}
