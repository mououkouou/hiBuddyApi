package com.example.hibuddy.api.application;

import com.example.hibuddy.api.domain.Report;
import com.example.hibuddy.api.domain.repository.ReportRepository;
import com.example.hibuddy.api.domain.support.ReportResource;
import com.example.hibuddy.api.domain.support.ReportType;
import com.example.hibuddy.api.interfaces.request.ReportRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class ReportServiceTest {

    @Autowired
    ReportService reportService;

    @Autowired
    ReportRepository reportRepository;

    @BeforeEach
    void setUp() {
        reportRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        reportRepository.deleteAll();
    }

    @Test
    @DisplayName("save는 ReportRequest를 받아 Report 객체를 저장합니다.")
    public void testSaveSuccess() throws Exception {
        //given
        Long resourceId = 0L;
        String content = "content";
        ReportType reportType = ReportType.ETC;
        ReportResource reportResource = ReportResource.COMPANION;

        ReportRequest reportRequest = new ReportRequest(resourceId, reportType, content, reportResource);

        //when
        Report savedReport = reportService.save(reportRequest);

        //then
        Report report = reportRepository.findById(savedReport.getId()).get();
        assertEquals(report.getId(), savedReport.getId());
        assertEquals(resourceId,savedReport.getResourceId());
        assertEquals(content,savedReport.getContent());
        assertEquals(reportType,savedReport.getReportType());
        assertEquals(reportResource, savedReport.getReportResource());
    }
}