package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepo extends JpaRepository<Group, Integer> {
}
