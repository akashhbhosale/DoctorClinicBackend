package com.doctorclinicapp.backend.repository.nursing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.nursing.NursingAssessmentMaster;

public interface NursingAssessmentRepository extends JpaRepository<NursingAssessmentMaster, Long> {

    Page<NursingAssessmentMaster> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
