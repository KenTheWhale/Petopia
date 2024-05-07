package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedBackRepo extends JpaRepository<Feedback, Integer> {
}
