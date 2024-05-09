package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepo extends JpaRepository<OrderDetail, Integer> {
}
