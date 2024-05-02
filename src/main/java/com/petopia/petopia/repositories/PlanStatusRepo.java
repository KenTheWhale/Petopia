package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.PlanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanStatusRepo extends JpaRepository<PlanStatus, Integer> {
}
