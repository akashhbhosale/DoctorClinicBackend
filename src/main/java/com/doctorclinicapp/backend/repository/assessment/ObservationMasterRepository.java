package com.doctorclinicapp.backend.repository.assessment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.assessment.ObservationMaster;

public interface ObservationMasterRepository extends JpaRepository<ObservationMaster, Long> {

    Page<ObservationMaster> findByObservationNameContainingIgnoreCase(String observationName, Pageable pageable);
}
