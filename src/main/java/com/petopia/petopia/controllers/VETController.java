package com.petopia.petopia.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vet")
@Tag(name = "VET")
public class VETController {

//    @PostMapping("/draft-report-creation")
//    @PreAuthorize("hasAuthority('vet:create')")
//    public DraftReportResponse createDraftReport(@RequestBody DraftReportRequest)

}
