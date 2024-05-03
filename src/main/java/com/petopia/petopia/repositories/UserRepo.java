package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
}
