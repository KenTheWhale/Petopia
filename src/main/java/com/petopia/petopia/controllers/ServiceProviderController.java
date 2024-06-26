package com.petopia.petopia.controllers;

import com.petopia.petopia.enums.Const;
import com.petopia.petopia.models.request_models.ConfirmDraftReportRequest;
import com.petopia.petopia.models.request_models.DraftReportRequest;
import com.petopia.petopia.models.request_models.EditDraftReportRequest;
import com.petopia.petopia.models.response_models.DraftReportResponse;
import com.petopia.petopia.services.SPService;
import io.swagger.v3.oas.annotations.Operation;
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

    @PostMapping("/draft-report")
    @PreAuthorize("hasAuthority('sp:create')")
    @Operation(description = Const.CREATOR_QUOC)
    public DraftReportResponse createDraftReport(@RequestBody DraftReportRequest request){
        return SPService.createDraftReport(request);
    }

    @PostMapping("/draft-report-content")
    @PreAuthorize("hasAuthority('sp:update')")
    @Operation(description = Const.CREATOR_TRIEU)
    public DraftReportResponse editDraftServiceReport(@RequestBody EditDraftReportRequest request){
        return SPService.editDraftReport(request);
    }

    @PostMapping("/draft-report-status")
    @PreAuthorize("hasAuthority('sp:update')")
    @Operation(description = Const.CREATOR_TRIEU)
    public DraftReportResponse confirmDraftServiceReport(@RequestBody ConfirmDraftReportRequest request){
        return SPService.confirmDraftReport(request);
    }

}
