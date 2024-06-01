package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart, Integer> {

    Optional<Cart> findByUser_IdAndCartStatus_Status(Integer userId, String status);
}
