package com.petopia.petopia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceProviderRepo extends JpaRepository<ServiceProvider, Integer> {

    Optional<ServiceProvider> findByIdAndServiceCenter_Id(Integer providerId, Integer centerId);
}
