package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.ServiceReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServiceReportRepo extends JpaRepository<ServiceReport, Integer> {

    Page<ServiceReport> findAllByAppointment_Pet_IdAndAppointment_TypeAndAppointment_AppointmentStatus_Status(Integer petID, String type, String status, Pageable pageable);
}
