package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepo extends JpaRepository<Token, Integer> {

    Optional<Token> findByAccount_IdAndTokenStatusStatusAndType(Integer accountId, String tokenStatus, String type);

    Optional<Token> findByValue(String value);

}
