package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.ServiceCenterPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceCenterPlanRepo extends JpaRepository<ServiceCenterPlan, Integer> {
}
