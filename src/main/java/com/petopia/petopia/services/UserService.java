package com.petopia.petopia.services;

import com.petopia.petopia.models.request_models.*;
import com.petopia.petopia.models.response_models.*;

public interface UserService {

    CurrentUserResponse getCurrentUserProfile();

    BlackListResponse viewBlackList();

    NotificationResponse viewNotification();

    HealthHistoryResponse getHealthHistoryList(HealthHistoryRequest request);

    PetListResponse getPetList();

    CreateAppointmentResponse createAppointment(CreateAppointmentRequest request, String type);

    ServiceListResponse getServiceList(ServiceCenterRequest request);

    LoadServicePageResponse loadServicePage(String type);

    BlackListResponse blockUser(BlockAndUnblockUserRequest request);

    BlackListResponse unblockUser(BlockAndUnblockUserRequest request);

    ServiceCenterDetailResponse getServiceCenterDetail(ServiceCenterRequest request);

    ServiceDetailResponse getServiceDetail(ServiceRequest request);

}
