package com.petopia.petopia.controllers;

import com.petopia.petopia.models.request_models.CreateAppointmentRequest;
import com.petopia.petopia.models.request_models.HealthHistoryRequest;
import com.petopia.petopia.models.request_models.UserRequest;
import com.petopia.petopia.models.response_models.CreateAppointmentResponse;
import com.petopia.petopia.models.response_models.HealthHistoryResponse;
import com.petopia.petopia.models.response_models.PetListResponse;
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

    @PostMapping("/userProfile")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<?> getUserProfile(@RequestBody UserRequest userRequest){
        return userService.getUserProfile(userRequest);
    }

    @PostMapping("/blackList")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<?> viewBlackList(@RequestBody UserRequest userRequest){
        return userService.viewBlackList(userRequest);
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
}
