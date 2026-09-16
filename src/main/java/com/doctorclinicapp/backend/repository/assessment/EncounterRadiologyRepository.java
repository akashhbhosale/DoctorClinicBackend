package com.doctorclinicapp.backend.repository.assessment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.assessment.EncounterRadiology;

public interface EncounterRadiologyRepository extends JpaRepository<EncounterRadiology, Long> {

    List<EncounterRadiology> findByEncounterIdOrderByCreatedAtDesc(Long encounterId);
}
