package com.doctorclinicapp.backend.repository.medication;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.medication.EncounterSurgicalTreatment;

public interface EncounterSurgicalTreatmentRepository extends JpaRepository<EncounterSurgicalTreatment, Long> {

    List<EncounterSurgicalTreatment> findByEncounterIdOrderByCreatedAtDesc(Long encounterId);
}
