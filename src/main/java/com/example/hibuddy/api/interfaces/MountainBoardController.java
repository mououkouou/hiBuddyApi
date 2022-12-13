package com.example.hibuddy.api.interfaces;

import com.example.hibuddy.api.application.MountainBoardService;
import com.example.hibuddy.api.domain.User;
import com.example.hibuddy.api.interfaces.response.MountainBoardView;
import com.example.hibuddy.api.interfaces.support.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mountain-boards")
@RequiredArgsConstructor
public class MountainBoardController {

    private final MountainBoardService mountainBoardService;


    @PutMapping
    public ResponseEntity<HttpStatus> upsertUserMountainBoard(Long userId, Long famousMountainId) {
        mountainBoardService.upsertBoards(userId, famousMountainId);

        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<MountainBoardView> getUserMountainBoardView(Long userId) {
        return ResponseEntity.ok(mountainBoardService.composeRegionMountainBoard(userId));
    }
}
