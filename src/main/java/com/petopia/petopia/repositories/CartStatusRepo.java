package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartStatusRepo extends JpaRepository<CartStatus, Integer> {
}
