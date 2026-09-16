package com.doctorclinicapp.backend.repository.assessment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.assessment.EncounterRadiologyFile;

public interface EncounterRadiologyFileRepository extends JpaRepository<EncounterRadiologyFile, Long> {

    List<EncounterRadiologyFile> findByRadiologyIdOrderByUploadedAtDesc(Long radiologyId);
}
