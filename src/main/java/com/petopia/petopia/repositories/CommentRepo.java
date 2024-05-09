package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
}
