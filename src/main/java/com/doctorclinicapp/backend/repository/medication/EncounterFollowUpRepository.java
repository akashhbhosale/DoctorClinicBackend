package com.doctorclinicapp.backend.repository.medication;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.medication.EncounterFollowUp;

public interface EncounterFollowUpRepository extends JpaRepository<EncounterFollowUp, Long> {

    Optional<EncounterFollowUp> findByEncounterId(Long encounterId);
}
