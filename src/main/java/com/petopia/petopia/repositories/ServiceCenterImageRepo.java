package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.ServiceCenterImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceCenterImageRepo extends JpaRepository<ServiceCenterImage, Integer> {

    @Query(value = "select s from ServiceCenterImage s where s.serviceCenter.id = :id")
    List<String> findAllImageByCenterId(@Param("id") Integer centerId);
}
