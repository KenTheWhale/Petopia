package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart, Integer> {
}
