package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepo extends JpaRepository<Service, Integer> {
}
