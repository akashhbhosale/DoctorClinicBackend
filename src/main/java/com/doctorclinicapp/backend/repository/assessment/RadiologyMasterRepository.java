package com.doctorclinicapp.backend.repository.assessment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.assessment.RadiologyMaster;

public interface RadiologyMasterRepository extends JpaRepository<RadiologyMaster, Long> {

    Page<RadiologyMaster> findByOrderNameContainingIgnoreCase(String orderName, Pageable pageable);
}
