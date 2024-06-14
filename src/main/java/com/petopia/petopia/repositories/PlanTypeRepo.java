package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.PlanType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanTypeRepo extends JpaRepository<PlanType, Integer> {
    PlanType findByType(String type);
}
