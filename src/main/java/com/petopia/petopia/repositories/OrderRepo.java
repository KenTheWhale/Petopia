package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  OrderRepo extends JpaRepository<Order, Integer> {
}
