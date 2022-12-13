package com.example.hibuddy.api.domain;

import com.example.hibuddy.api.domain.support.ReportResource;
import com.example.hibuddy.api.domain.support.ReportType;
import com.example.hibuddy.api.interfaces.request.ReportRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long resourceId;

    @Enumerated(value = EnumType.STRING)
    private ReportResource reportResource;

    @Enumerated(value = EnumType.STRING)
    private ReportType reportType;

    private String content;

    public static Report of(ReportRequest reportRequest) {
        return Report.builder()
                .resourceId(reportRequest.getResourceId())
                .reportResource(reportRequest.getReportResource())
                .content(reportRequest.getContent())
                .reportType(reportRequest.getReportType())
                .build();
    }
}
