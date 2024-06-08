package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Integer> {
}
