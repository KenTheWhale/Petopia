package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.PetImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PetImageRepo extends JpaRepository<PetImage, Integer> {

    @Query(value = "select p.link from PetImage p where p.pet.id = :petId")
    List<String> findAllImageByPetId(@Param("petId") Integer petId);
}
