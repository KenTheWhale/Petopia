package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.GroupStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupStatusRepo extends JpaRepository<GroupStatus, Integer> {
}
