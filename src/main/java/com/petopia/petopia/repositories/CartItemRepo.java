package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItem, Integer> {
}
