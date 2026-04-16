package com.doctorclinicapp.backend.repository.assessment;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.assessment.EncounterVitals;

public interface EncounterVitalsRepository extends JpaRepository<EncounterVitals, Long> {

    Optional<EncounterVitals> findByEncounterId(Long encounterId);

    boolean existsByEncounterId(Long encounterId);
}