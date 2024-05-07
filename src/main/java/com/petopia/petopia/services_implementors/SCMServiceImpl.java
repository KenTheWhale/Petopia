package com.petopia.petopia.services_implementors;

import com.petopia.petopia.models.entity_models.Appointment;
import com.petopia.petopia.models.request_models.AppointmentProcessingRequest;
import com.petopia.petopia.models.response_models.AppointmentProcessingResponse;
import com.petopia.petopia.models.response_models.AvailableServiceProviderListResponse;
import com.petopia.petopia.repositories.AppointmentRepo;
import com.petopia.petopia.services.SCMService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SCMServiceImpl implements SCMService {

    private final AppointmentRepo appointmentRepo;
    @Override
    public AppointmentProcessingResponse processAppointment(AppointmentProcessingRequest request) {
        Appointment appointment = appointmentRepo.findById(request.getAppointmentId()).orElse(null);
        if(appointment == null){
            return AppointmentProcessingResponse.builder().status("400").message("No existed appointment with id " + request.getAppointmentId()).build();
        }

        return null;
    }

    @Override
    public AvailableServiceProviderListResponse getAvailableServiceProvider() {
        return null;
    }
}
