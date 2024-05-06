package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceProviderRepo extends JpaRepository<ServiceProvider, Integer> {
}
