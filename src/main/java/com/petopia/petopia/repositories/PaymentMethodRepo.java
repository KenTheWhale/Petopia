package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepo extends JpaRepository<PaymentMethod, Integer> {
}
