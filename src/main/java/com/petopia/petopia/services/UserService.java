package com.petopia.petopia.services;

import com.petopia.petopia.models.request_models.HealthHistoryRequest;
import com.petopia.petopia.models.request_models.UserRequest;
import com.petopia.petopia.models.response_models.HealthHistoryResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> getUserProfile(UserRequest userRequest);

    ResponseEntity<?> viewBlackList(UserRequest userRequest);

    HealthHistoryResponse getHealthHistoryList(HealthHistoryRequest request);
}
