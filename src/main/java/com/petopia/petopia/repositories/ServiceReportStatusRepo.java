package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.ServiceReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceReportStatusRepo extends JpaRepository<ServiceReportStatus, Integer> {

    ServiceReportStatus findByStatus(String status);

}
