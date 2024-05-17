package com.petopia.petopia.controllers;

import com.petopia.petopia.models.request_models.*;
import com.petopia.petopia.models.request_models.CreateAppointmentRequest;
import com.petopia.petopia.models.request_models.HealthHistoryRequest;
import com.petopia.petopia.models.request_models.ServiceRequest;
import com.petopia.petopia.models.request_models.UserRequest;
import com.petopia.petopia.models.request_models.*;
import com.petopia.petopia.models.response_models.*;
import com.petopia.petopia.services.AccountService;
import com.petopia.petopia.services.SCMService;
import com.petopia.petopia.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "User")
public class UserController {

    private final UserService userService;
    private final AccountService accountService;
    private final SCMService scmService;

    @GetMapping("/user-profile")
    @PreAuthorize("hasAuthority('user:read')")
    public CurrentUserResponse getCurrentUserProfile(){
        return userService.getCurrentUserProfile();
    }

    @GetMapping("/blackList")
    @PreAuthorize("hasAuthority('user:read')")
    public BlackListResponse viewBlackList(){
        return userService.viewBlackList();
    }

    @GetMapping("/notification")
    @PreAuthorize("hasAuthority('user:read')")
    public NotificationResponse viewNotification(){
        return userService.viewNotification();
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

    @PostMapping("/service-list")
    @PreAuthorize("hasAuthority('user:read')")
    public ServiceListResponse getServiceList(@RequestBody ServiceCenterRequest request){
        return userService.getServiceList(request);
    }

    @PostMapping("/health-appointment-creation")
    @PreAuthorize("hasAuthority('user:create')")
    public CreateAppointmentResponse createHealthAppointment(@RequestBody CreateAppointmentRequest request){
        return userService.createAppointment(request, "health");
    }

    @PostMapping("/service-appointment-creation")
    @PreAuthorize("hasAuthority('user:create')")
    public CreateAppointmentResponse createServiceAppointment(@RequestBody CreateAppointmentRequest request){
        return userService.createAppointment(request, "service");
    }

    @PostMapping("/health-center-and-service-page")
    @PreAuthorize("hasAuthority('user:read')")
    public LoadServicePageResponse loadHealthServicePage(){
        return userService.loadServicePage("health");
    }

    @PostMapping("/service-center-and-service-page")
    @PreAuthorize("hasAuthority('user:read')")
    public LoadServicePageResponse loadServiceServicePage(){
        return userService.loadServicePage("service");
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
    @PostMapping("/service-center-detail")
    @PreAuthorize("hasAuthority('user:read')")
    public ServiceCenterDetailResponse getServiceCenterDetail(@RequestBody ServiceCenterRequest request){
        return userService.getServiceCenterDetail(request);
    }
    @PostMapping("/service-detail")
    @PreAuthorize("hasAuthority('user:read')")
    public ServiceDetailResponse getServiceDetail(@RequestBody ServiceRequest request){
        return userService.getServiceDetail(request);
    }

    @PostMapping("/user-block")
    @PreAuthorize("hasAuthority('user:update')")
    public BlackListResponse blockUser(@RequestBody BlockUserRequest request){
        return userService.blockUser(request);
    }
    @PostMapping("/user-unblock")
    @PreAuthorize("hasAuthority('user:update')")
    public BlackListResponse unBlockUser(@RequestBody BlockAndUnblockUserRequest request){
        return userService.unblockUser(request);
    }

}
