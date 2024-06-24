package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeSlotRepo extends JpaRepository<TimeSlot, Integer> {

    List<TimeSlot> findAllByServiceCenter_Id(Integer centerId);
}
