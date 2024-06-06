package com.petopia.petopia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedBackRepo extends JpaRepository<Feedback, Integer> {
    List<Feedback> findAllByProduct_Id(int id);
}
