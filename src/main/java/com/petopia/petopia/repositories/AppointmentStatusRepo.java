package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentStatusRepo extends JpaRepository<AppointmentStatus, Integer> {

    //get AppointmentStatus by status
    AppointmentStatus findByStatus(String status);
}
