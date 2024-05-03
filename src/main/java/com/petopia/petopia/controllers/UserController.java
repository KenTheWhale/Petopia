package com.petopia.petopia.controllers;

import com.petopia.petopia.models.request_models.HealthHistoryRequest;
import com.petopia.petopia.models.request_models.UserRequest;
import com.petopia.petopia.models.response_models.HealthHistoryResponse;
import com.petopia.petopia.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/health-history")
    @PreAuthorize("hasAuthority('user:read')")
    public HealthHistoryResponse getHealthHistoryList(@RequestBody HealthHistoryRequest request){
        return userService.getHealthHistoryList(request);
    }
}
