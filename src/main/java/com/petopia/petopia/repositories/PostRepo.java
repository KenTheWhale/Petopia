package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post, Integer> {
}
