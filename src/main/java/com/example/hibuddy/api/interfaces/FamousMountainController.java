package com.example.hibuddy.api.interfaces;

import com.example.hibuddy.api.application.FamousMountainService;
import com.example.hibuddy.api.application.KoreaTourService;
import com.example.hibuddy.api.application.RegionBoardService;
import com.example.hibuddy.api.application.support.QuizDto;
import com.example.hibuddy.api.domain.FamousMountain;
import com.example.hibuddy.api.interfaces.response.FamousMountainTourView;
import com.example.hibuddy.api.interfaces.response.FamousMountainView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/famous-mountains")
public class FamousMountainController {

    private final FamousMountainService famousMountainService;
    private final KoreaTourService koreaTourService;

    @GetMapping("/{id}")
    public ResponseEntity<FamousMountain> getById(@PathVariable Long id) {
        FamousMountain famousMountain = famousMountainService.getById(id);

        return ResponseEntity.ok(famousMountain);
    }

    @GetMapping("/quiz")
    public ResponseEntity<QuizDto> getRandomQuiz() {
        return ResponseEntity.ok(famousMountainService.generateRandomQuiz());
    }

    @GetMapping
    public ResponseEntity<List<FamousMountainView>> getAllFamousMountains(Long userId) {
        List<FamousMountain> mountains = famousMountainService.findAllMountains(userId);

        return ResponseEntity.ok(mountains.stream().map(FamousMountainView::of).collect(Collectors.toList()));
    }

    @GetMapping("/info")
    public ResponseEntity<FamousMountainTourView> getFamousMountainInfoById(@RequestParam Long id) throws URISyntaxException {
        FamousMountain famousMountain = famousMountainService.getById(id);
        return ResponseEntity.ok(FamousMountainTourView.of(famousMountain, koreaTourService.getFamousMountainInfo(famousMountain.getContentTypeId(), famousMountain.getContentId()).block().get(0),koreaTourService.getFamousMountainIntro(famousMountain.getContentTypeId(), famousMountain.getContentId()).block().get(0)));
    }
}
