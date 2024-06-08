package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.Appointment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepo extends JpaRepository<Appointment, Integer> {
    //find list appointment by appointment status
    List<Appointment> findAllByAppointmentStatus_Status(String status);

    @Query(
            value = "select a from Appointment a " +
                    "where a.serviceProvider.id = :id " +
                    "and a.appointmentStatus.status <> :status " +
                    "and a.date < :date"
    )
    List<Appointment> findAllAppointmentByProviderIdAndNotStatusAndBeforeDate(
            @Param("id") Integer providerId,
            @Param("status") String status,
            @Param("date") LocalDateTime date
    );

    Optional<Appointment> findByIdAndCenterId(Integer appointmentId, Integer centerId);
}
