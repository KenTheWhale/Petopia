package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {

    //find user by id
    @Query("SELECT u FROM User u WHERE u.id = :id")
    User findUserById(@Param("id") Integer id);

}
