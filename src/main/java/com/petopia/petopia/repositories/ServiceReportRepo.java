package com.petopia.petopia.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceReportRepo extends JpaRepository<ServiceReport, Integer> {

    Page<ServiceReport> findAllByAppointment_Pet_IdAndAppointment_TypeAndAppointment_AppointmentStatus_Status(Integer petID, String type, String status, Pageable pageable);

    ServiceReport findByAppointment_Id(Integer appointmentId);
}
