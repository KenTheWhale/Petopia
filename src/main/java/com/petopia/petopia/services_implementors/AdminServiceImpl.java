package com.petopia.petopia.services_implementors;

import com.petopia.petopia.enums.Const;
import com.petopia.petopia.models.entity_models.BusinessPlan;
import com.petopia.petopia.models.entity_models.PlanType;
import com.petopia.petopia.models.request_models.CreatePlanRequest;
import com.petopia.petopia.models.response_models.CreatePlanResponse;
import com.petopia.petopia.models.response_models.GetPlanTypeResponse;
import com.petopia.petopia.repositories.BusinessPlanRepo;
import com.petopia.petopia.repositories.PlanStatusRepo;
import com.petopia.petopia.repositories.PlanTypeRepo;
import com.petopia.petopia.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final BusinessPlanRepo businessPlanRepo;
    private final PlanStatusRepo planStatusRepo;
    private final PlanTypeRepo planTypeRepo;

    @Override
    public GetPlanTypeResponse getAllType() {
        List<PlanType> planTypes = planTypeRepo.findAll();
        return GetPlanTypeResponse.builder()
                .status("200")
                .message("Lấy danh sách loại gói doanh nghiệp thành công")
                .types(
                      planTypes.stream().map(
                              t -> new GetPlanTypeResponse.Type(t.getId(), t.getType())
                      ).toList()
                )
                .build();
    }

    @Override
    public CreatePlanResponse createPlan(CreatePlanRequest request) {
        List<BusinessPlan> businessPlans = businessPlanRepo.findAll();
        if(checkIfNameIsAlreadyExisted(businessPlans, request.getName(), request.getType())) {
            return CreatePlanResponse.builder()
                    .status("400")
                    .message("Tên gói doanh nghiệp đã tồn tại")
                    .build();
        }

        businessPlanRepo.save(
                BusinessPlan.builder()
                        .planStatus(planStatusRepo.findByStatus(Const.PLAN_STATUS_ACTIVE))
                        .name(request.getName())
                        .fee(request.getFee())
                        .duration(request.getDuration())
                        .description(request.getDescription())
                        .planType(planTypeRepo.findByType(request.getType().trim()))
                        .build()
        );
        return CreatePlanResponse.builder().status("200").message("Tạo gói thành công").build();
    }

    private boolean checkIfNameIsAlreadyExisted(List<BusinessPlan> businessPlans, String name, String type){
        for(BusinessPlan b: businessPlans){
            if(b.getName().equals(name) && b.getPlanType().getType().equals(type)) return true;
        }
        return false;
    }
}
