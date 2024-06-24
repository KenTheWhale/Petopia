package com.petopia.petopia.controllers;

import com.petopia.petopia.enums.Const;
import com.petopia.petopia.models.request_models.AppointmentProcessingRequest;
import com.petopia.petopia.models.request_models.AvailableServiceProviderListRequest;
import com.petopia.petopia.models.response_models.AppointmentProcessingResponse;
import com.petopia.petopia.models.response_models.AvailableServiceProviderListResponse;
import com.petopia.petopia.models.response_models.RequestingAppointmentResponse;
import com.petopia.petopia.services.SCMService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/scm")
@Tag(name = "Service center manager")
public class ServiceCenterManagerController {

    private final SCMService scmService;

    @PostMapping("/available-service-provider-list")
    @PreAuthorize("hasAuthority('scm:read')")
    @Operation(description = Const.CREATOR_QUOC)
    public AvailableServiceProviderListResponse getAvailableServiceProvider(@RequestBody AvailableServiceProviderListRequest request){
        return scmService.getAvailableServiceProvider(request);
    }

    @PostMapping("/appointment-processing")
    @PreAuthorize("hasAuthority('scm:update')")
    @Operation(description = Const.CREATOR_QUOC)
    public AppointmentProcessingResponse processAppointment(@RequestBody AppointmentProcessingRequest request){
        return scmService.processAppointment(request);
    }

    @GetMapping("/requesting-appointment-list")
    @PreAuthorize("hasAuthority('scm:read')")
    @Operation(description = Const.CREATOR_TRIEU)
    public RequestingAppointmentResponse getRequestingAppointmentList(){
        return scmService.viewRequestingAppointment();
    }
}
