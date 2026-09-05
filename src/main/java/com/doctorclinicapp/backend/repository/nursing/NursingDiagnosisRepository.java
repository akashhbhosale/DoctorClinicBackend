package com.doctorclinicapp.backend.repository.nursing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.nursing.NursingDiagnosisMaster;

public interface NursingDiagnosisRepository extends JpaRepository<NursingDiagnosisMaster, Long> {

    Page<NursingDiagnosisMaster> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
