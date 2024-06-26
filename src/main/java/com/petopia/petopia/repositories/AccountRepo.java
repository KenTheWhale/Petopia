package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account, Integer> {

    Optional<Account> findByEmail(String email);



}
