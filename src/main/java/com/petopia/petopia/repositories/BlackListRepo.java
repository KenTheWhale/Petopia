package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListRepo extends JpaRepository<BlackList, Integer>{
}
