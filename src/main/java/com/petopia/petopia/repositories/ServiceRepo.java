package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.Services;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceRepo extends JpaRepository<Services, Integer> {
    @NonNull
    Optional<Services> findById(Integer id);

    //get all service and sort by rating
    List<Services> findAllByServiceCenter_TypeAndServiceStatus_StatusOrderByRatingDesc(String type, String status);
}
