package com.doctorclinicapp.backend.repository.nursing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.nursing.NursingInterventionMaster;

public interface NursingInterventionRepository extends JpaRepository<NursingInterventionMaster, Long> {

    Page<NursingInterventionMaster> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
