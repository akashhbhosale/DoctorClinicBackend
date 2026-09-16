package com.doctorclinicapp.backend.repository.assessment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.assessment.EncounterObservation;

public interface EncounterObservationRepository extends JpaRepository<EncounterObservation, Long> {

    List<EncounterObservation> findByEncounterIdOrderByCreatedAtDesc(Long encounterId);
}
