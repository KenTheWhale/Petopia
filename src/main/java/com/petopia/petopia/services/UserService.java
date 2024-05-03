package com.petopia.petopia.services;

import com.petopia.petopia.models.request_models.UserRequest;
import com.petopia.petopia.models.response_models.UserProfileResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> getUserProfile(UserRequest userRequest);

    ResponseEntity<?> viewBlackList(UserRequest userRequest);
import com.petopia.petopia.models.request_models.HealthHistoryRequest;
import com.petopia.petopia.models.response_models.HealthHistoryResponse;

public interface UserService {
    HealthHistoryResponse getHealthHistoryList(HealthHistoryRequest request);
}
