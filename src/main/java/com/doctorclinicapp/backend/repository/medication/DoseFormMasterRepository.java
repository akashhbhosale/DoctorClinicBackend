package com.doctorclinicapp.backend.repository.medication;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.medication.DoseFormMaster;

public interface DoseFormMasterRepository extends JpaRepository<DoseFormMaster, Long> {

    Page<DoseFormMaster> findByDoseFormNameContainingIgnoreCase(String doseFormName, Pageable pageable);
}
