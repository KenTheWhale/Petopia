package com.petopia.petopia.controllers;

import com.petopia.petopia.models.request_models.LoginRequest;
import com.petopia.petopia.models.response_models.LoginResponse;
import com.petopia.petopia.services.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){
        return authenticationService.login(request);
    }



}
