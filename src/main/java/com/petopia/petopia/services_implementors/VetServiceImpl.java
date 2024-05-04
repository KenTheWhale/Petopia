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
import com.petopia.petopia.services.VetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VetServiceImpl implements VetService {

    private final AuthenticationService authenticationService;

    private final PetRepo petRepo;

    private final AppointmentRepo appointmentRepo;

    @Override
    public DraftReportResponse createDraftReport(DraftReportRequest request) {
        if(request.getReport().isEmpty()){
            return DraftReportResponse.builder()
                    .status("400")
                    .message("Report must not be empty")
                    .detail(null)
                    .build();
        }

        Account doctor = authenticationService.getCurrentLoggedUser();
        assert doctor != null;
        Appointment appointment = appointmentRepo.findById(request.getAppointmentId()).orElse(null);
        if(appointment == null){
            return DraftReportResponse.builder()
                    .status("400")
                    .message("Appointment is not existed")
                    .detail(null)
                    .build();
        }

        Pet pet = appointment.getPet();
        if(pet == null){
            return DraftReportResponse.builder()
                    .status("400")
                    .message("Pet is not existed")
                    .detail(null)
                    .build();
        }

        User owner = pet.getUser();

        return DraftReportResponse.builder()
                .status("200")
                .message("Create draft report successfully")
                .detail(
                        DraftReportResponse.DraftResponse.builder()
                                .ownerName(owner.getAccount().getName())
                                .ownerAddress(owner.getAddress())
                                .ownerPhone(owner.getPhone())
                                .petName(pet.getName())
                                .petGender(pet.getGender())
                                .petAge(pet.getAge())
                                .petType(pet.getType())
                                .doctorName(doctor.getName())
                                .date(LocalDateTime.now())
                                .location(appointment.getLocation())
                                .report(request.getReport())
                                .extraDetail(request.getExtraContent())
                                .fee(appointment.getFee())
                                .build()
                )
                .build();
    }
}
