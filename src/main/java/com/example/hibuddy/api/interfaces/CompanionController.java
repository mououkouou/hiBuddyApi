package com.example.hibuddy.api.interfaces;

import com.example.hibuddy.api.application.CompanionService;
import com.example.hibuddy.api.interfaces.request.CompanionRequest;
import com.example.hibuddy.api.interfaces.response.CompanionView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/companions")
public class CompanionController {
    private final CompanionService companionService;

    @PostMapping
    public ResponseEntity<CompanionView> save(@RequestBody CompanionRequest companionRequest) {
        return new ResponseEntity<CompanionView>(CompanionView.of(companionService.save(companionRequest)), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanionView> getCompanionById(@PathVariable Long id) {
        return new ResponseEntity<CompanionView>(CompanionView.of(companionService.getCompanionById(id)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CompanionView>> getCompanions(@RequestParam int page,
                                                             @RequestParam(defaultValue = "30")
                                                             @Min(value = 1, message = "size는 1이상이여야 합니다.")
                                                             @Max(value = 100, message = "size는 100이하여야합니다.") int size) {
        return new ResponseEntity<List<CompanionView>>(companionService.getCompanions(page, size)
                    .stream().map(CompanionView::of).collect(Collectors.toList()), HttpStatus.OK);
    }
}
