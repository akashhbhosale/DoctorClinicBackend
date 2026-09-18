package com.doctorclinicapp.backend.repository.medication;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.medication.DirectionOfUseMaster;

public interface DirectionOfUseMasterRepository extends JpaRepository<DirectionOfUseMaster, Long> {

    Page<DirectionOfUseMaster> findByDirectionNameContainingIgnoreCase(String directionName, Pageable pageable);
}
