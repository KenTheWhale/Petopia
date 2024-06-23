package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.Product;
import com.petopia.petopia.models.entity_models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepo extends JpaRepository<ProductImage, Integer>{
    List<ProductImage> findAllByProduct(Product product);
}