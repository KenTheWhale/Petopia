package com.petopia.petopia.services;

import com.petopia.petopia.models.request_models.CreateAppointmentRequest;
import com.petopia.petopia.models.request_models.CreateUserProfileRequest;
import com.petopia.petopia.models.request_models.HealthHistoryRequest;
import com.petopia.petopia.models.request_models.UserRequest;
import com.petopia.petopia.models.response_models.*;
import org.springframework.http.ResponseEntity;

public interface UserService {

    CurrentUserResponse getCurrentUserProfile();

    BlackListResponse viewBlackList();

    ResponseEntity<?> viewNotification(UserRequest userRequest);

    HealthHistoryResponse getHealthHistoryList(HealthHistoryRequest request);

    PetListResponse getPetList();

    CreateAppointmentResponse createAppointment(CreateAppointmentRequest request);

    CreateUserProfileResponse createUserProfile(int id, CreateUserProfileRequest request);
}
