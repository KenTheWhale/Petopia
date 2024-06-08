package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSlotRepo extends JpaRepository<TimeSlot, Integer> {
}
