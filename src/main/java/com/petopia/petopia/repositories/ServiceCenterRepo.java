package com.petopia.petopia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceCenterRepo extends JpaRepository<ServiceCenter, Integer> {


    //get all serviceCenter and sort by rating
    List<ServiceCenter> findAllByTypeAndServiceCenterStatus_StatusOrServiceCenterStatus_StatusOrderByRatingDesc(String type, String status, String status2);

}
