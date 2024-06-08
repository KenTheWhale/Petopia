package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.TimeSlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSlotStatusRepo extends JpaRepository<TimeSlotStatus, Integer> {
}
