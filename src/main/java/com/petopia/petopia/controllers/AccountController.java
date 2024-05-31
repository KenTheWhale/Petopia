package com.petopia.petopia.controllers;

import com.petopia.petopia.models.request_models.RefreshRequest;
import com.petopia.petopia.models.response_models.LogoutResponse;
import com.petopia.petopia.models.response_models.RefreshResponse;
import com.petopia.petopia.services.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
@Tag(name = "Account")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/refresh")
    public RefreshResponse refresh(){
        return accountService.refresh();
    }

    @GetMapping("/logout")
    public LogoutResponse logout(){
        return accountService.logout();
    }
}
