package com.petopia.petopia.controllers;

import com.petopia.petopia.enums.Const;
import com.petopia.petopia.models.request_models.CreateAccountRequest;
import com.petopia.petopia.models.request_models.LoginRequest;
import com.petopia.petopia.models.response_models.CreateAccountResponse;
import com.petopia.petopia.models.response_models.LoginResponse;
import com.petopia.petopia.services.AccountService;
import com.petopia.petopia.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    @Operation(description = Const.CREATOR_HUY)
    public CreateAccountResponse createAccount(@RequestBody CreateAccountRequest request){
        return  authenticationService.Register(request);
    }
    @GetMapping("/awake")
    @Operation(description = Const.CREATOR_HUY)
    public String awake() {return  "Sever still alive";}

    @PostMapping("/login")
    @Operation(description = Const.CREATOR_QUOC)
    public LoginResponse login(@RequestBody LoginRequest request){
        return authenticationService.login(request);
    }
}
