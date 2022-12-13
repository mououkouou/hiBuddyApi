package com.example.hibuddy.api.interfaces.request;

import com.example.hibuddy.api.domain.support.ReportResource;
import com.example.hibuddy.api.domain.support.ReportType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequest {
    private Long resourceId;
    private ReportType reportType;
    private String content;
    private ReportResource reportResource;
}
