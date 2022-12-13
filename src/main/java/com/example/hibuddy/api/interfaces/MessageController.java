package com.example.hibuddy.api.interfaces;

import com.example.hibuddy.api.application.MessageService;
import com.example.hibuddy.api.domain.User;
import com.example.hibuddy.api.interfaces.request.MessageRequest;
import com.example.hibuddy.api.interfaces.response.MessageResponse;
import com.example.hibuddy.api.interfaces.support.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageResponse> save(@AuthUser User user, @RequestBody MessageRequest messageRequest) {
        return new ResponseEntity<MessageResponse>(MessageResponse.of(messageService.save(messageRequest.getRoomId(), messageRequest.getContent(), user)), HttpStatus.CREATED);
    }
}