package com.petopia.petopia.services;

import com.petopia.petopia.models.request_models.AppointmentProcessingRequest;
import com.petopia.petopia.models.response_models.AppointmentProcessingResponse;
import com.petopia.petopia.models.response_models.AvailableServiceProviderListResponse;

public interface SCMService {

    AppointmentProcessingResponse processAppointment(AppointmentProcessingRequest request);

    AvailableServiceProviderListResponse getAvailableServiceProvider();
}
