package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.ServiceCenter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceCenterRepo extends JpaRepository<ServiceCenter, Integer> {
}
