package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification, Integer> {

    List<Notification> findAllByUser_Id(Integer userId);

}
