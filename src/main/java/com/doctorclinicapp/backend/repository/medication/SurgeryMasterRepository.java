package com.doctorclinicapp.backend.repository.medication;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.medication.SurgeryMaster;

public interface SurgeryMasterRepository extends JpaRepository<SurgeryMaster, Long> {

    Page<SurgeryMaster> findBySurgeryNameContainingIgnoreCase(String surgeryName, Pageable pageable);
}
