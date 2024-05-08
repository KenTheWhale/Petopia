package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.ServiceCenter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceCenterRepo extends JpaRepository<ServiceCenter, Integer> {

    Optional<ServiceCenter> findServiceCenterByName(String name);

}
