package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepo extends JpaRepository<Notification, Integer> {
}
