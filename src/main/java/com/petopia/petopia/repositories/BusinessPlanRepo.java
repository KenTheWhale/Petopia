package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.BusinessPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessPlanRepo extends JpaRepository<BusinessPlan, Integer> {
}
