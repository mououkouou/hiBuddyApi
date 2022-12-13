package com.example.hibuddy.api.interfaces;

import com.example.hibuddy.api.application.ScrapService;
import com.example.hibuddy.api.domain.Scrap;
import com.example.hibuddy.api.interfaces.request.ScrapMgetRequest;
import com.example.hibuddy.api.interfaces.request.ScrapRequest;
import com.example.hibuddy.api.interfaces.response.ScrapListView;
import com.example.hibuddy.api.interfaces.response.ScrapView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/scraps")
public class ScrapController {
    private final ScrapService scrapService;

    @PostMapping
    public ResponseEntity<ScrapView> scrapResource(@RequestBody
                                                           ScrapRequest request) {
        return new ResponseEntity<ScrapView>(ScrapView.of(scrapService.save(request)), HttpStatus.CREATED);
    }

    @PostMapping("/mget")
    public ResponseEntity<List<ScrapListView>> mgetScraps(@RequestBody
                                                                  ScrapMgetRequest request) {
        List<Scrap> scraps = scrapService.getScrapsByResourceIdsAndType(request);
        return new ResponseEntity<List<ScrapListView>>(ScrapListView.buildScrapListView(scraps, request.getResourceIds(),
                request.getResourceType()), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ScrapView>> getScrapsByUserId(Long userId) {
        List<Scrap> scraps = scrapService.getScrapsByUserId(userId);

        return new ResponseEntity<List<ScrapView>>(scraps.size() > 0 ? scraps.stream()
                .map(ScrapView::of)
                .toList() : List.of(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> cancelScrap(@PathVariable Long id) {
        scrapService.deleteById(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }
}
