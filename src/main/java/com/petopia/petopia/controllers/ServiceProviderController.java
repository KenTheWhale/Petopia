package com.petopia.petopia.controllers;

import com.petopia.petopia.models.request_models.DraftReportRequest;
import com.petopia.petopia.models.response_models.DraftReportResponse;
import com.petopia.petopia.services.SPService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sp")
@Tag(name = "Service provider")
public class ServiceProviderController {

    private final SPService SPService;

    @PostMapping("/draft-report-creation")
    @PreAuthorize("hasAuthority('sp:create')")
    public DraftReportResponse createDraftReport(@RequestBody DraftReportRequest request){
        return SPService.createDraftReport(request);
    }

}
