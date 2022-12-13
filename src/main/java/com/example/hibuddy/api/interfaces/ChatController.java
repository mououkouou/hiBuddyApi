package com.example.hibuddy.api.interfaces;

import com.example.hibuddy.api.application.ChatService;
import com.example.hibuddy.api.application.PromiseService;
import com.example.hibuddy.api.domain.User;
import com.example.hibuddy.api.interfaces.response.RoomListView;
import com.example.hibuddy.api.interfaces.response.RoomView;
import com.example.hibuddy.api.interfaces.support.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats")
public class ChatController {
    private final ChatService chatService;
    private final PromiseService promiseService;

    @PostMapping
    public ResponseEntity<RoomView> save(@AuthUser User user, @RequestParam Long companionId) {
        return new ResponseEntity<RoomView>(RoomView.of(chatService.save(user, companionId), user, false), HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<RoomView> enterRoom(@AuthUser User user, @PathVariable Long id) {
        return new ResponseEntity<RoomView>(RoomView.of(chatService.getRoomById(user, id), user, promiseService.existsPromise(user.getId(), id)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RoomListView>> getRoomsByUserId(@AuthUser User user) {
        return new ResponseEntity<List<RoomListView>>(chatService.getRoomsByUserId(user)
                .stream().map(r -> RoomListView.of(r, user)).collect(Collectors.toList()), HttpStatus.OK);
    }

}
