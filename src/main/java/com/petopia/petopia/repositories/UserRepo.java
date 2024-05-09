package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByAccountId(int accountId);

    //find user by id
    Optional<User> findUserById(Integer id);
}
