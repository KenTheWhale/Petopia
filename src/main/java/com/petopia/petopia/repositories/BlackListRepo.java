package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BlackListRepo extends JpaRepository<BlackList, Integer>{

    Optional<BlackList> findByBlockedUserIdAndUser_Id(Integer blockedUserId, Integer userId);

    @Transactional
    void deleteByBlockedUserIdAndUser_Id(Integer blockedUserId, Integer userId);

}
