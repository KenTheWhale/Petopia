package com.petopia.petopia.controllers;

import com.petopia.petopia.enums.Const;
import com.petopia.petopia.models.request_models.CreatePlanRequest;
import com.petopia.petopia.models.response_models.CreatePlanResponse;
import com.petopia.petopia.models.response_models.GetPlanTypeResponse;
import com.petopia.petopia.services.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Tag(name = "Admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/plan/type")
    @PreAuthorize("hasAuthority('admin:read')")
    @Operation(description = Const.CREATOR_QUOC)
    public GetPlanTypeResponse getAllType(){
        return adminService.getAllType();
    }

    @PostMapping("/plan/create")
    @PreAuthorize("hasAuthority('admin:create')")
    @Operation(description = Const.CREATOR_QUOC)
    public CreatePlanResponse createPlan(@RequestBody CreatePlanRequest request){
        return adminService.createPlan(request);
    }
}
