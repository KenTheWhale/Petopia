package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.ServiceImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceImageRepo extends JpaRepository<ServiceImage, Integer> {

    @Query(value = "select s from ServiceImage s where s.services.id = :id")
    List<String> findAllImageByServiceId(@Param("id") Integer serviceId);
}
