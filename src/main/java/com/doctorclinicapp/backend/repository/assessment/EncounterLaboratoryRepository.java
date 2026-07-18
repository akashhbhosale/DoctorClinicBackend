package com.doctorclinicapp.backend.repository.assessment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.assessment.EncounterLaboratory;

public interface EncounterLaboratoryRepository extends JpaRepository<EncounterLaboratory, Long> {

    List<EncounterLaboratory> findByEncounterIdOrderByCreatedAtDesc(Long encounterId);
}