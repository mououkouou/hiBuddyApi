package com.example.hibuddy.api.interfaces;

import com.example.hibuddy.api.application.UserService;
import com.example.hibuddy.api.interfaces.request.ModifyUserRequest;
import com.example.hibuddy.api.interfaces.request.ProfileImageRequest;
import com.example.hibuddy.api.interfaces.request.UserRequest;
import com.example.hibuddy.api.interfaces.response.ProfileImageView;
import com.example.hibuddy.api.interfaces.response.UserView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserView> register(@RequestBody UserRequest request) {
        return new ResponseEntity<UserView>(UserView.of(userService.register(request)), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserView> getUserById(@PathVariable Long id) {
        return new ResponseEntity<UserView>(UserView.of(userService.getUserById(id)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<UserView> getUserByNicknameOrEmail(@RequestParam(required = false) String nickname, @RequestParam(required = false) String email) {
        if (nickname != null) {
            return new ResponseEntity<UserView>(UserView.of(userService.getUserByNickname(nickname)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(UserView.of(userService.validEmail(email)),HttpStatus.OK);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserView> modify(@PathVariable Long id, @RequestBody ModifyUserRequest request) {
        return new ResponseEntity<UserView>(UserView.of(userService.modify(id, request)), HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id, @RequestParam String email) {
        userService.delete(id, email);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

    @PostMapping("/{id}/profile-image")
    public ResponseEntity<ProfileImageView> upsertProfileImage(@PathVariable Long id,
                                                                  @ModelAttribute ProfileImageRequest request) throws IOException {
        String image = userService.upsertUserProfile(request, id);
        return ResponseEntity.ok(ProfileImageView.of(image));
    }
}
