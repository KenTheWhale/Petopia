package com.petopia.petopia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepo extends JpaRepository<Pet, Integer> {

    List<Pet> findAllByUser_Account_IdOrderById(Integer userID);

    //find Pet by petName and userId
    Pet findByIdAndUser_Account_Id(Integer id, Integer userId);
}
