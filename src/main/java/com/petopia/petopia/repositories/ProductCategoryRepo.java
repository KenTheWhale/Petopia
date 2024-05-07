package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepo extends JpaRepository<ProductCategory, Integer> {
}
