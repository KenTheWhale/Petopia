package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.SubstituteStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubstituteStatusRepo extends JpaRepository<SubstituteStatus, Integer> {

    SubstituteStatus findByStatus(String status);

}
