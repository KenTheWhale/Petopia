package com.petopia.petopia.controllers;

import com.petopia.petopia.models.request_models.DraftReportRequest;
import com.petopia.petopia.models.response_models.DraftReportResponse;
import com.petopia.petopia.services.VetService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vet")
@Tag(name = "VET")
public class VETController {

    private final VetService vetService;

    @PostMapping("/draft-report-creation")
    @PreAuthorize("hasAuthority('vet:create')")
    public DraftReportResponse createDraftReport(@RequestBody DraftReportRequest request){
        return vetService.createDraftReport(request);
    }

}
