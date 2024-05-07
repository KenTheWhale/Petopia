package com.petopia.petopia.controllers;

import com.petopia.petopia.models.request_models.AppointmentProcessingRequest;
import com.petopia.petopia.models.response_models.AppointmentProcessingResponse;
import com.petopia.petopia.models.response_models.AvailableServiceProviderListResponse;
import com.petopia.petopia.services.SCMService;
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

    @GetMapping("/available-service-provider-list")
    @PreAuthorize("hasAuthority('scm:read')")
    public AvailableServiceProviderListResponse getAvailableServiceProvider(){
        return scmService.getAvailableServiceProvider();
    }

    @PostMapping("/appointment-processing")
    @PreAuthorize("hasAuthority('scm:update')")
    public AppointmentProcessingResponse processAppointment(@RequestBody AppointmentProcessingRequest request){
        return scmService.processAppointment(request);
    }
}
