package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.ServiceCenterStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceCenterStatusRepo extends JpaRepository<ServiceCenterStatus, Integer> {
}
