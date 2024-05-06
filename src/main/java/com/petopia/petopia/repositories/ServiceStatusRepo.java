package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.ServiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceStatusRepo extends JpaRepository<ServiceStatus, Integer> {
}
