package com.petopia.petopia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductAttributeRepo extends JpaRepository<ProductAttribute, Integer> {

    @Query(value = "select a.name from ProductAttribute a where a.product.id = :id")
    List<String> findAllAttributeName(@Param("id") int productId);
}
