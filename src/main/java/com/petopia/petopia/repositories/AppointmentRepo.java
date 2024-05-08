package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.Appointment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepo extends JpaRepository<Appointment, Integer> {

    List<Appointment> findAllByPet_User_IdAndTypeAndAppointmentStatus_Status(Integer ownerID, String type, String status, Pageable pageable);

    List<Appointment> findAllByPet_User_Id(Integer userId);
}
