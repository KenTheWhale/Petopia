package com.petopia.petopia.services_implementors;

import com.petopia.petopia.enums.Const;
import com.petopia.petopia.models.entity_models.Appointment;
import com.petopia.petopia.models.request_models.AppointmentProcessingRequest;
import com.petopia.petopia.models.response_models.AppointmentProcessingResponse;
import com.petopia.petopia.models.response_models.AvailableServiceProviderListResponse;
import com.petopia.petopia.models.response_models.RequestingAppointmentResponse;
import com.petopia.petopia.repositories.AppointmentRepo;
import com.petopia.petopia.services.SCMService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public RequestingAppointmentResponse viewRequestingAppointment() {

        List<Appointment> appointmentList = appointmentRepo.findAllByAppointmentStatus_Status(Const.APPOINTMENT_STATUS_PENDING);

        if(!appointmentList.isEmpty()){
            return RequestingAppointmentResponse.builder()
                    .status("200")
                    .message("Get requesting appointment list successfully")
                    .requestingAppointmentList(appointmentList.stream().map(appointment -> RequestingAppointmentResponse.RequestingAppointment.builder()
                            .id(appointment.getId())
                            .petName(appointment.getPet().getName())
                            .status(appointment.getAppointmentStatus().getStatus())
                            .type(appointment.getType())
                            .location(appointment.getServiceProvider().getServiceCenter().getAddress())
                            .date(appointment.getDate())
                            .appointmentServiceList(appointment.getAppointmentServiceList().stream()
                                    .map(appointmentService -> RequestingAppointmentResponse.AppointmentService.builder()
                                    .id(appointmentService.getId())
                                    .serviceName(appointmentService.getServices().getName())
                                    .build()).collect(Collectors.toList()))
                            .fee(appointment.getFee())
                            .build()).collect(Collectors.toList()))
                    .build();
        }
        return RequestingAppointmentResponse.builder()
                .status("400")
                .message("No requesting appointment")
                .requestingAppointmentList(Collections.emptyList())
                .build();
    }
}
