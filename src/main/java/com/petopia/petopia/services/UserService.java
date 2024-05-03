package com.petopia.petopia.services;

import com.petopia.petopia.models.request_models.HealthHistoryRequest;
import com.petopia.petopia.models.response_models.HealthHistoryResponse;

public interface UserService {
    HealthHistoryResponse getHealthHistoryList(HealthHistoryRequest request);
}
