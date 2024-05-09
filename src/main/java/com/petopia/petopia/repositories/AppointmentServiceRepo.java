package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.AppointmentService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentServiceRepo extends JpaRepository<AppointmentService, Integer> {
}
