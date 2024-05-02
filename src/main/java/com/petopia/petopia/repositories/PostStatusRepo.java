package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostStatusRepo extends JpaRepository<PostStatus, Integer> {

}
