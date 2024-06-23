package com.petopia.petopia.controllers;

import com.petopia.petopia.models.request_models.CreateAccountRequest;
import com.petopia.petopia.models.request_models.LoginRequest;
import com.petopia.petopia.models.request_models.ResetPasswordRequest;
import com.petopia.petopia.models.request_models.SendOtpRequest;
import com.petopia.petopia.models.response_models.CreateAccountResponse;
import com.petopia.petopia.models.response_models.LoginResponse;
import com.petopia.petopia.models.response_models.ResetPasswordResponse;
import com.petopia.petopia.models.response_models.SendOTPResponse;
import com.petopia.petopia.services.AccountService;
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
    private final AccountService accountService;
    @PostMapping("/register")
    public CreateAccountResponse createAccount(@RequestBody CreateAccountRequest request){
        return  authenticationService.Register(request);
    }
    @GetMapping("/awake")
    public String awake() {return  "Sever still alive";}

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){
        return authenticationService.login(request);
    }

    @PostMapping("/send/otp")
    public SendOTPResponse sendOTP(@RequestBody SendOtpRequest request){
        return authenticationService.sendOTP(request);
    }
    @PostMapping("/reset/password")
    public ResetPasswordResponse resetPassword(@RequestBody ResetPasswordRequest request){
        return authenticationService.resetPassword(request);
    }
}
