package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepo extends JpaRepository<Pet, Integer> {

    List<Pet> findAllByUser_Account_IdOrderById(Integer userID);

    //find Pet by petName and userId
    Pet findByNameAndUser_Account_Id(String petName, Integer userId);
}
