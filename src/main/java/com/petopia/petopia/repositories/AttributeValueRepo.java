package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.AttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttributeValueRepo extends JpaRepository<AttributeValue, Integer> {
}
