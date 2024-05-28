package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountStatusRepo extends JpaRepository<AccountStatus, Integer> {

}
