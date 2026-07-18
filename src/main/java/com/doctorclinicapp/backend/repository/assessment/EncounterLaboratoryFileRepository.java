package com.doctorclinicapp.backend.repository.assessment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.assessment.EncounterLaboratoryFile;

public interface EncounterLaboratoryFileRepository extends JpaRepository<EncounterLaboratoryFile, Long> {

    List<EncounterLaboratoryFile> findByLaboratoryIdOrderByUploadedAtDesc(Long laboratoryId);
}