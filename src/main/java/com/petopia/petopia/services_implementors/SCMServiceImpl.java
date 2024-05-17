package com.petopia.petopia.services_implementors;

import com.petopia.petopia.enums.Const;
import com.petopia.petopia.models.entity_models.Account;
import com.petopia.petopia.models.entity_models.Appointment;
import com.petopia.petopia.models.entity_models.ServiceCenter;
import com.petopia.petopia.models.entity_models.ServiceProvider;
import com.petopia.petopia.models.request_models.AppointmentProcessingRequest;
import com.petopia.petopia.models.request_models.AvailableServiceProviderListRequest;
import com.petopia.petopia.models.response_models.AppointmentProcessingResponse;
import com.petopia.petopia.models.response_models.AvailableServiceProviderListResponse;
import com.petopia.petopia.models.response_models.RequestingAppointmentResponse;
import com.petopia.petopia.repositories.AppointmentRepo;
import com.petopia.petopia.repositories.ServiceCenterRepo;
import com.petopia.petopia.repositories.ServiceProviderRepo;
import com.petopia.petopia.services.AccountService;
import com.petopia.petopia.services.SCMService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SCMServiceImpl implements SCMService {

    private final AppointmentRepo appointmentRepo;

    private final AccountService accountService;

    private final ServiceCenterRepo serviceCenterRepo;

    private final ServiceProviderRepo serviceProviderRepo;

    @Override
    public AppointmentProcessingResponse processAppointment(AppointmentProcessingRequest request) {
//        Account currentAcc = accountService.getCurrentLoggedAccount();
//        assert currentAcc != null;
//        Appointment appointment = appointmentRepo.findByIdAndCenterId(request.getAppointmentId(), currentAcc.getServiceCenter().getId()).orElse(null);
//        if(appointment == null) return AppointmentProcessingResponse.builder().status("400").message("Cuộc hẹn này không thuộc về cơ sở dịch vụ này").providers(Collections.emptyList()).build();
//        ServiceProvider serviceProvider = serviceProviderRepo.findByIdAndServiceCenter_Id(request.getProviderId(), currentAcc.getServiceCenter().getId()).orElse(null);
//

        return null;
    }

    @Override
    public AvailableServiceProviderListResponse getAvailableServiceProvider(AvailableServiceProviderListRequest request) {
        Account currentSCMAcc = accountService.getCurrentLoggedAccount();
        if(currentSCMAcc == null) return AvailableServiceProviderListResponse.builder().status("400").message("Tài khoản không hợp lệ").providers(Collections.emptyList()).build();
        ServiceCenter center = currentSCMAcc.getServiceCenter();
        Appointment appointment = appointmentRepo.findByIdAndCenterId(request.getAppointmentId(), currentSCMAcc.getServiceCenter().getId()).orElse(null);
        if(appointment == null) return AvailableServiceProviderListResponse.builder().status("400").message("Cuộc hẹn này không thuộc về cơ sở dịch vụ này").providers(Collections.emptyList()).build();
        List<AvailableServiceProviderListResponse.ProviderResponse> list = new ArrayList<>();

        for(ServiceProvider provider: center.getServiceProviderList()){
            if(checkIfProviderIsReadyToBeAssigned(appointment.getDate(), provider.getId())){
                list.add(
                        AvailableServiceProviderListResponse.ProviderResponse.builder()
                                .id(provider.getId())
                                .name(provider.getAccount().getName())
                                .build()
                );
            }
        }
        return AvailableServiceProviderListResponse.builder()
                .status("200")
                .message("Get available service provider successfully")
                .providers(list)
                .build();
    }

    private List<Appointment> getAllAppointmentAssignedToProviderThatNotCompleted(LocalDateTime requestDate, Integer providerId){
        return appointmentRepo.findAllAppointmentByProviderIdAndNotStatusAndBeforeDate(providerId, Const.APPOINTMENT_STATUS_SUCCESSFUL, requestDate);
    }

    private boolean checkIfProviderIsReadyToBeAssigned(LocalDateTime requestDate, Integer providerId){
        List<Appointment> appointments = getAllAppointmentAssignedToProviderThatNotCompleted(requestDate, providerId);
        return appointments.isEmpty();
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
