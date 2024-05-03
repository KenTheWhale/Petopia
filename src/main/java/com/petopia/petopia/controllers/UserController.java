package com.petopia.petopia.controllers;

import com.petopia.petopia.models.request_models.UserRequest;
import com.petopia.petopia.models.response_models.UserProfileResponse;
import com.petopia.petopia.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/getUserProfile")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<?> getUserProfile(@RequestBody UserRequest userRequest){
        return userService.getUserProfile(userRequest);
    }

    @GetMapping("/viewBlackList")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<?> viewBlackList(@RequestBody UserRequest userRequest){
        return userService.viewBlackList(userRequest);
    }
}
