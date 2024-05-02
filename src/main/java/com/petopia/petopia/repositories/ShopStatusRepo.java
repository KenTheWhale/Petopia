package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.ShopStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopStatusRepo extends JpaRepository<ShopStatus, Integer> {
}
