package com.petopia.petopia.services;

import com.petopia.petopia.models.entity_models.TimeSlot;
import com.petopia.petopia.models.request_models.*;
import com.petopia.petopia.models.response_models.*;

public interface UserService {

    UserResponse getCurrentUserProfile();

    BlackListResponse viewBlackList();

    NotificationResponse viewNotification();

    HealthHistoryResponse getHealthHistoryList(HealthHistoryRequest request);

    PetListResponse getPetList();

    CreateUserProfileResponse createUserProfile(CreateUserProfileRequest request);

    UpdateUserProfileResponse updateUserProfile(UpdateUserProfileRequest request);

    CreateAppointmentResponse createAppointment(CreateAppointmentRequest request, String type);

    ServiceListResponse getServiceList(ServiceCenterRequest request);

    LoadServicePageResponse loadServicePage(String type);

    BlackListResponse blockUser(BlockAndUnblockUserRequest request);

    BlackListResponse unblockUser(BlockAndUnblockUserRequest request);

    ServiceCenterDetailResponse getServiceCenterDetail(ServiceCenterRequest request);

    ServiceDetailResponse getServiceDetail(ServiceRequest request);

    ProductReportResponse reportProduct(ProductReportRequest request);

    ViewOtherUserProfileResponse viewOtherUserProfile(ViewOtherUserProfileRequest request);

    FindOtherUserProfileResponse findOtherUserProfileResponse(FindOtherUserProfileRequest request);

    ViewFeedbackListResponse viewFeedbackListResponse(ViewFeedbackListRequest request);

    ViewShopProfileResponse viewShopProfile(ViewShopProfileRequest request);

    SearchShopResponse searchShop(SearchShopRequest request);

    TimeSlotResponse getTimeSlot(TimeSlotRequest request);

}
