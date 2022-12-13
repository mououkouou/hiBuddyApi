package com.example.hibuddy.api.interfaces;

import com.example.hibuddy.api.application.VisitedMountainService;
import com.example.hibuddy.api.domain.VisitedMountain;
import com.example.hibuddy.api.domain.support.RegionType;
import com.example.hibuddy.api.interfaces.response.VisitedMountainView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/visited-mountains")
public class VisitedMountainController {

    private final VisitedMountainService visitedMountainService;

    @GetMapping
    public ResponseEntity<List<Long>> getByUser(Long userId) {
        List<VisitedMountain> mountains = visitedMountainService.getByUser(userId);

        return ResponseEntity.ok(mountains.stream()
                .map(mountain -> mountain.getFamousMountain()
                        .getId())
                .collect(Collectors.toList()));
    }

    @GetMapping("/{regionType}/counts")
    public ResponseEntity<VisitedMountainView> getByUserAndRegionType(Long userId,
                                                                      @PathVariable RegionType regionType) {

        var result = visitedMountainService.getByUserAndRegion(userId, regionType);
        return ResponseEntity.ok(VisitedMountainView.of(result.getFirst(), regionType, result.getSecond()));
    }
}
