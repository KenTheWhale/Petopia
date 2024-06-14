package com.petopia.petopia.services;

import com.petopia.petopia.models.request_models.CreatePlanRequest;
import com.petopia.petopia.models.response_models.CreatePlanResponse;
import com.petopia.petopia.models.response_models.GetPlanTypeResponse;

public interface AdminService {

    GetPlanTypeResponse getAllType();
    CreatePlanResponse createPlan(CreatePlanRequest request);
}
