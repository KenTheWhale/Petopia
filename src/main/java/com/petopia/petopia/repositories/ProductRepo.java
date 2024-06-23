package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer> {

    Product findProductById(int id);

    List<Product> findAllByShopId(int shopId);

}
