package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepo extends JpaRepository<Shop, Integer> {
}
