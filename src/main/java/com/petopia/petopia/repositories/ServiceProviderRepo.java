package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceProviderRepo extends JpaRepository<ServiceProvider, Integer> {

    Optional<ServiceProvider> findByIdAndServiceCenter_Id(Integer providerId, Integer centerId);
}
