package com.doctorclinicapp.backend.repository.assessment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.assessment.LaboratoryMaster;

public interface LaboratoryMasterRepository extends JpaRepository<LaboratoryMaster, Long> {

    Page<LaboratoryMaster> findByTestNameContainingIgnoreCase(String testName, Pageable pageable);
}