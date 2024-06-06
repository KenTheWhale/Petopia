package com.petopia.petopia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountStatusRepo extends JpaRepository<AccountStatus, Integer> {
    AccountStatus findByStatus(String status);
}
