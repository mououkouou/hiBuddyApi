package com.example.hibuddy.api.interfaces;

import com.example.hibuddy.api.application.AlarmService;
import com.example.hibuddy.api.interfaces.response.AlarmResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alarms")
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping
    public ResponseEntity<List<AlarmResponse>> getAlarmsByUserId(@RequestParam Long userId) {
        return new ResponseEntity<List<AlarmResponse>>(alarmService.getAlarmsByUserId(userId).stream().map(AlarmResponse::of).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<HttpStatus> readAlarm(@PathVariable Long id) {
        alarmService.readAlarm(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }
}
