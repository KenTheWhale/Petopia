package com.petopia.petopia.controllers;

import com.petopia.petopia.enums.Const;
import com.petopia.petopia.models.request_models.*;
import com.petopia.petopia.models.response_models.*;
import com.petopia.petopia.services.AccountService;
import com.petopia.petopia.services.SCMService;
import com.petopia.petopia.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
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

    @GetMapping("/user-profile")
    @PreAuthorize("hasAuthority('user:read')")
    @Operation(description = Const.CREATOR_TRIEU)
    public UserResponse getCurrentUserProfile(){
        return userService.getCurrentUserProfile();
    }

    @GetMapping("/blacklist")
    @PreAuthorize("hasAuthority('user:read')")
    @Operation(description = Const.CREATOR_TRIEU)
    public BlackListResponse viewBlackList(){
        return userService.viewBlackList();
    }

    @GetMapping("/notification")
    @PreAuthorize("hasAuthority('user:read')")
    @Operation(description = Const.CREATOR_TRIEU)
    public NotificationResponse viewNotification(){
        return userService.viewNotification();
    }

    @PostMapping("/health-history")
    @PreAuthorize("hasAuthority('user:read')")
    @Operation(description = Const.CREATOR_QUOC)
    public HealthHistoryResponse getHealthHistoryList(@RequestBody HealthHistoryRequest request){
        return userService.getHealthHistoryList(request);
    }

    @GetMapping("/pet-list")
    @PreAuthorize("hasAuthority('user:read')")
    @Operation(description = Const.CREATOR_QUOC)
    public PetListResponse getPetListByUserId(){
        return userService.getPetList();
    }

    @PostMapping("/service-list")
    @PreAuthorize("hasAuthority('user:read')")
    @Operation(description = Const.CREATOR_TRIEU)
    public ServiceListResponse getServiceList(@RequestBody ServiceCenterRequest request){
        return userService.getServiceList(request);
    }

    @PostMapping("/health-appointment-creation")
    @PreAuthorize("hasAuthority('user:create')")
    @Operation(description = Const.CREATOR_TRIEU)
    public CreateAppointmentResponse createHealthAppointment(@RequestBody CreateAppointmentRequest request){
        return userService.createAppointment(request, "health");
    }

    @PostMapping("/service-appointment-creation")
    @PreAuthorize("hasAuthority('user:create')")
    @Operation(description = Const.CREATOR_TRIEU)
    public CreateAppointmentResponse createServiceAppointment(@RequestBody CreateAppointmentRequest request){
        return userService.createAppointment(request, "service");
    }

    @PostMapping("/time-slot/get")
    @PreAuthorize("hasAuthority('user:read')")
    public TimeSlotResponse getTimeSlot(@RequestBody TimeSlotRequest request){
        return userService.getTimeSlot(request);
    }

    @GetMapping("/health-center-and-service-page")
    @PreAuthorize("hasAuthority('user:read')")
    @Operation(description = Const.CREATOR_TRIEU)
    public LoadServicePageResponse loadHealthServicePage(){
        return userService.loadServicePage("health");
    }

    @GetMapping("/service-center-and-service-page")
    @PreAuthorize("hasAuthority('user:read')")
    @Operation(description = Const.CREATOR_TRIEU)
    public LoadServicePageResponse loadServiceServicePage(){
        return userService.loadServicePage("service");
    }

    @PostMapping("/profile-update")
    @PreAuthorize("hasAuthority('user:update')")
    @Operation(description = Const.CREATOR_HUY)
    public UpdateUserProfileResponse updateUserProfile( @RequestBody UpdateUserProfileRequest request) {
        return userService.updateUserProfile(request);
    }

    @PostMapping("/service-center-detail")
    @PreAuthorize("hasAuthority('user:read')")
    @Operation(description = Const.CREATOR_TRIEU)
    public ServiceCenterDetailResponse getServiceCenterDetail(@RequestBody ServiceCenterRequest request){
        return userService.getServiceCenterDetail(request);
    }
    @PostMapping("/service-detail")
    @PreAuthorize("hasAuthority('user:read')")
    @Operation(description = Const.CREATOR_TRIEU)
    public ServiceDetailResponse getServiceDetail(@RequestBody ServiceRequest request){
        return userService.getServiceDetail(request);
    }

    @PostMapping("/user-block")
    @PreAuthorize("hasAuthority('user:update')")
    @Operation(description = Const.CREATOR_HUY)
    public BlackListResponse blockUser(@RequestBody BlockAndUnblockUserRequest request){
        return userService.blockUser(request);
    }
    @PostMapping("/user-unblock")
    @PreAuthorize("hasAuthority('user:update')")
    @Operation(description = Const.CREATOR_TRIEU)
    public BlackListResponse unBlockUser(@RequestBody BlockAndUnblockUserRequest request){
        return userService.unblockUser(request);
    }

    @GetMapping("/account/delete")
    @PreAuthorize("hasAuthority('user:delete')")
    @Operation(description = Const.CREATOR_HUY)
    public void accountDelete() {
         accountService.deleteAccount();
    }

    @PostMapping("/search")
    @PreAuthorize("hasAuthority('user:read')")
    @Operation(description = Const.CREATOR_HUY)
    public FindOtherUserProfileResponse findOtherUserProfile(@RequestBody FindOtherUserProfileRequest request) {
        return userService.findOtherUserProfileResponse(request);
    }

    @PostMapping("/product-report")
    @PreAuthorize("hasAuthority('user:create')")
    @Operation(description = Const.CREATOR_QUOC)
    public ProductReportResponse reportProduct(@RequestBody ProductReportRequest request){
        return userService.reportProduct(request);
    }

    @PostMapping("/feedback/list")
    @PreAuthorize("hasAuthority('user:read')")
    @Operation(description = Const.CREATOR_HUY)
    public ViewFeedbackListResponse viewFeedbackList(@RequestBody ViewFeedbackListRequest request) {
        return userService.viewFeedbackListResponse(request);
    }



    @PostMapping("/other/profile")
    @PreAuthorize("hasAuthority('user:read')")
    @Operation(description = Const.CREATOR_HUY)
    public ViewOtherUserProfileResponse viewOtherUserProfile(@RequestBody ViewOtherUserProfileRequest request) {
        return userService.viewOtherUserProfile(request);
    }
    @PostMapping("/shop/profile")
    @PreAuthorize("hasAuthority('user:read')")
    @Operation(description = Const.CREATOR_HUY)
    public ViewShopProfileResponse viewShopProfileResponse(@RequestBody ViewShopProfileRequest request) {
        return userService.viewShopProfile(request);
    }

    @PostMapping("/shop/search")
    @PreAuthorize("hasAuthority('user:read')")
    @Operation(description = Const.CREATOR_HUY)
    public SearchShopResponse searchShopResponse(@RequestBody SearchShopRequest request) {
        return userService.searchShop(request);
    }

    @GetMapping("/product/page")
    @PreAuthorize("hasAuthority('user:read')")
    @Operation(description = Const.CREATOR_HUY)
    public ViewProductPageResponse viewProductPageResponse(){
        return userService.viewProductPageResponse();
    }

    @PostMapping("/product/detail")
    @PreAuthorize("hasAuthority('user:read')")
    @Operation(description = Const.CREATOR_HUY)
    public ViewProductDetailResponse viewProductDetailResponse(@RequestBody ViewProductDetailRequest request) {
        return userService.viewProductDetail(request);
    }

}
