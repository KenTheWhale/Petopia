package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token, Integer> {

    Optional<Token> findByAccount_IdAndTokenStatusStatusAndType(Integer accountId, String tokenStatus, String type);

    Optional<Token> findByValue(String value);

}
