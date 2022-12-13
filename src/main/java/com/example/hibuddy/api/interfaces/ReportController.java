package com.example.hibuddy.api.interfaces;

import com.example.hibuddy.api.application.ReportService;
import com.example.hibuddy.api.interfaces.request.ReportRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<HttpStatus> save(@RequestBody ReportRequest reportRequest) {
        reportService.save(reportRequest);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }
}
