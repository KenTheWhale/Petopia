package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.PostLikedUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikedUserRepo extends JpaRepository<PostLikedUser, Integer> {
}
