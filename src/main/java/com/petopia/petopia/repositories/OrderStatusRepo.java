package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepo extends JpaRepository<OrderStatus, Integer> {

}
