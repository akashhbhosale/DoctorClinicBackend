package com.doctorclinicapp.backend.repository.medication;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.medication.EncounterMedication;

public interface EncounterMedicationRepository extends JpaRepository<EncounterMedication, Long> {

    List<EncounterMedication> findByEncounterIdOrderByCreatedAtDesc(Long encounterId);
}
