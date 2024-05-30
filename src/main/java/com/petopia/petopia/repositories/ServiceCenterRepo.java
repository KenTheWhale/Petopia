package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.ServiceCenter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceCenterRepo extends JpaRepository<ServiceCenter, Integer> {


    //get all serviceCenter and sort by rating
    List<ServiceCenter> findAllByTypeAndServiceCenterStatus_StatusOrServiceCenterStatus_StatusOrderByRatingDesc(String type, String status, String status2);

}
