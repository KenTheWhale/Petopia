package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStatusRepo extends JpaRepository<ProductStatus, Integer> {
}
