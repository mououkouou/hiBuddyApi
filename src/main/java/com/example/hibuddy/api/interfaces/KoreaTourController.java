package com.example.hibuddy.api.interfaces;

import com.example.hibuddy.api.application.KoreaTourService;
import com.example.hibuddy.api.interfaces.response.MountainView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/korea-tour")
@RequiredArgsConstructor
public class KoreaTourController {

    private final KoreaTourService koreaTourService;

    @GetMapping
    public ResponseEntity<Flux<MountainView>> getMountains(@RequestParam String keyword) throws UnsupportedEncodingException, URISyntaxException {
        return new ResponseEntity<Flux<MountainView>>(koreaTourService.getMountains(keyword).
                map(koreaTours -> koreaTours.stream().filter(Objects::nonNull).map(MountainView::of).collect(Collectors.toList())).flatMapMany(Flux::fromIterable), HttpStatus.OK);
    }
}
