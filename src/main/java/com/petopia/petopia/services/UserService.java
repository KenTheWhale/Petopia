package com.petopia.petopia.services;

import com.petopia.petopia.models.request_models.BlockAndUnblockUserRequest;
import com.petopia.petopia.models.request_models.CreateAppointmentRequest;
import com.petopia.petopia.models.request_models.CreateUserProfileRequest;
import com.petopia.petopia.models.request_models.HealthHistoryRequest;
import com.petopia.petopia.models.request_models.ServiceRequest;
import com.petopia.petopia.models.request_models.*;
import com.petopia.petopia.models.response_models.*;

public interface UserService {

    CurrentUserResponse getCurrentUserProfile();

    BlackListResponse viewBlackList();

    NotificationResponse viewNotification();

    HealthHistoryResponse getHealthHistoryList(HealthHistoryRequest request);

    PetListResponse getPetList();

    CreateUserProfileResponse createUserProfile(int id, CreateUserProfileRequest request);

    CreateAppointmentResponse createAppointment(CreateAppointmentRequest request, String type);

    ServiceListResponse getServiceList(ServiceCenterRequest request);

    LoadServicePageResponse loadServicePage(String type);

    BlackListResponse blockUser(BlockAndUnblockUserRequest request);

    BlackListResponse unblockUser(BlockAndUnblockUserRequest request);

    ServiceCenterDetailResponse getServiceCenterDetail(ServiceCenterRequest request);

    ServiceDetailResponse getServiceDetail(ServiceRequest request);

}
