package com.petopia.petopia.controllers;

import com.petopia.petopia.models.request_models.*;
import com.petopia.petopia.models.response_models.*;
import com.petopia.petopia.services.AccountService;
import com.petopia.petopia.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "User")
public class UserController {

    private final UserService userService;
    private final AccountService accountService;

    @GetMapping("/userProfile")
    @PreAuthorize("hasAuthority('user:read')")
    public CurrentUserResponse getCurrentUserProfile(){
        return userService.getCurrentUserProfile();
    }

    @GetMapping("/blackList")
    @PreAuthorize("hasAuthority('user:read')")
    public BlackListResponse viewBlackList(){
        return userService.viewBlackList();
    }

    @PostMapping("/notification")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<?> viewNotification(@RequestBody UserRequest userRequest){
        return userService.viewNotification(userRequest);
    }

    @PostMapping("/health-history")
    @PreAuthorize("hasAuthority('user:read')")
    public HealthHistoryResponse getHealthHistoryList(@RequestBody HealthHistoryRequest request){
        return userService.getHealthHistoryList(request);
    }

    @PostMapping("/pet-list")
    @PreAuthorize("hasAuthority('user:read')")
    public PetListResponse getPetListByUserId(){
        return userService.getPetList();
    }

    @PostMapping("/appointment-creation")
    @PreAuthorize("hasAuthority('user:create')")
    public CreateAppointmentResponse createAppointment(@RequestBody CreateAppointmentRequest request){
        return userService.createAppointment(request);
    }
    @PostMapping("/create-user-profile")
    public CreateUserProfileResponse createUserProfile(
            @RequestParam("accountId") int accountId,
            @RequestBody CreateUserProfileRequest request) {
         return userService.createUserProfile(accountId, request);
    }
    @PostMapping("/create-Account")
    public CreateAccountResponse createAccount(@RequestBody CreateAccountRequest request){
        return  accountService.createAccount(request);
    }
}
