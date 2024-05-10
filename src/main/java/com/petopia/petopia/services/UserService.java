package com.petopia.petopia.services;

import com.petopia.petopia.models.request_models.CreateAppointmentRequest;
import com.petopia.petopia.models.request_models.HealthHistoryRequest;
import com.petopia.petopia.models.request_models.ServiceRequest;
import com.petopia.petopia.models.response_models.*;

public interface UserService {

    CurrentUserResponse getCurrentUserProfile();

    BlackListResponse viewBlackList();

    NotificationResponse viewNotification();

    HealthHistoryResponse getHealthHistoryList(HealthHistoryRequest request);

    PetListResponse getPetList();

    CreateAppointmentResponse createAppointment(CreateAppointmentRequest request, String type);

    ServiceListResponse getServiceList(ServiceRequest request);

    LoadServicePageResponse loadServicePage(String type);

}
