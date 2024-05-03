package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepo extends JpaRepository<Pet, Integer> {
}
