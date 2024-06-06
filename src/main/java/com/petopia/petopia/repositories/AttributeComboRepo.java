package com.petopia.petopia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttributeComboRepo extends JpaRepository<AttributeCombo, Integer> {

    Optional<AttributeCombo> findByMANAndMAVNAndSANAndSAVN(String MAN, String MAVN, String SAN, String SAVN);
}
