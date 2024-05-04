package com.petopia.petopia.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vet")
@Tag(name = "VET")
public class VETController {

    @GetMapping("/getVetProfile")
    public String getVetProfile(){
        return "Vet Profile";
    }

//    @PostMapping("/draft-report-creation")
//    @PreAuthorize("hasAuthority('vet:create')")
//    public DraftReportResponse createDraftReport(@RequestBody DraftReportRequest)

}
