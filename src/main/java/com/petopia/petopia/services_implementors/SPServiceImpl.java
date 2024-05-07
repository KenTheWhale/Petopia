package com.petopia.petopia.services_implementors;

import com.petopia.petopia.models.entity_models.Account;
import com.petopia.petopia.models.entity_models.Appointment;
import com.petopia.petopia.models.entity_models.Pet;
import com.petopia.petopia.models.entity_models.User;
import com.petopia.petopia.models.request_models.DraftReportRequest;
import com.petopia.petopia.models.response_models.DraftReportResponse;
import com.petopia.petopia.repositories.AppointmentRepo;
import com.petopia.petopia.repositories.PetRepo;
import com.petopia.petopia.services.AuthenticationService;
import com.petopia.petopia.services.SPService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SPServiceImpl implements SPService {

    private final AuthenticationService authenticationService;

    private final PetRepo petRepo;

    private final AppointmentRepo appointmentRepo;

    @Override
    public DraftReportResponse createDraftReport(DraftReportRequest request) {
        return null;
    }
}
