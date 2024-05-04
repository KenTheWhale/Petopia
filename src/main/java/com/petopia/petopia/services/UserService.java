package com.petopia.petopia.services;

import com.petopia.petopia.models.request_models.CreateAppointmentRequest;
import com.petopia.petopia.models.request_models.HealthHistoryRequest;
import com.petopia.petopia.models.request_models.UserRequest;
import com.petopia.petopia.models.response_models.CreateAppointmentResponse;
import com.petopia.petopia.models.response_models.HealthHistoryResponse;
import com.petopia.petopia.models.response_models.PetListResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> getUserProfile(UserRequest userRequest);

    ResponseEntity<?> viewBlackList(UserRequest userRequest);

    ResponseEntity<?> viewNotification(UserRequest userRequest);

    HealthHistoryResponse getHealthHistoryList(HealthHistoryRequest request);

    PetListResponse getPetList();

    CreateAppointmentResponse createAppointment(CreateAppointmentRequest request);
}
