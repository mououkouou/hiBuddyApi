package com.example.hibuddy.api.interfaces;

import com.example.hibuddy.api.application.EmailService;
import com.example.hibuddy.api.domain.support.MailType;
import com.example.hibuddy.api.interfaces.request.EmailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/emails")
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<HttpStatus> sendEmail(@RequestBody EmailRequest request) {
        emailService.sendEmail(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Boolean> verifyEmailCode(@PathVariable String email, String code, MailType type) {
        return new ResponseEntity<>(emailService.verifyEmailCode(email, code, type), HttpStatus.OK);
    }
}
