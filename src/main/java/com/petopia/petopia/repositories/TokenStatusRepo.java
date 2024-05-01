package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.TokenStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenStatusRepo extends JpaRepository<TokenStatus, Integer> {

    Optional<TokenStatus> findByStatus(String status);
}
