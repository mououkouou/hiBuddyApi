package com.example.hibuddy.api.interfaces;

import com.example.hibuddy.api.application.PromiseService;
import com.example.hibuddy.api.domain.User;
import com.example.hibuddy.api.interfaces.request.PromiseRequest;
import com.example.hibuddy.api.interfaces.response.ProfilePromiseView;
import com.example.hibuddy.api.interfaces.response.PromiseResponse;
import com.example.hibuddy.api.interfaces.support.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/promises")
public class PromiseController {

    private final PromiseService promiseService;

    @PostMapping
    public ResponseEntity<PromiseResponse> promiseCompanion(@AuthUser User user, @RequestBody PromiseRequest promiseRequest) {
        return new ResponseEntity<PromiseResponse>(PromiseResponse.of(promiseService.save(promiseRequest, user), user.getNickname()), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProfilePromiseView>> getPromisesByUserId(@RequestParam Long userId) {
        return new ResponseEntity<List<ProfilePromiseView>>(promiseService.getPromisesById(userId)
                .stream().map(ProfilePromiseView::of).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countPromises(@RequestParam Long userId) {
        return new ResponseEntity<Long>(promiseService.countPromises(userId), HttpStatus.OK);
    }
}
