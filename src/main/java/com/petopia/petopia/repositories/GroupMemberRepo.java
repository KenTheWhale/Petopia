package com.petopia.petopia.repositories;

import com.petopia.petopia.models.entity_models.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMemberRepo extends JpaRepository<GroupMember, Integer> {
}
