package com.doctorclinicapp.backend.repository.nursing;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.nursing.NursingAssessmentRecord;

public interface NursingAssessmentRecordRepository extends JpaRepository<NursingAssessmentRecord, Long> {

    List<NursingAssessmentRecord> findByEncounterIdOrderByCreatedAtDesc(Long encounterId);

    List<NursingAssessmentRecord> findByEncounter_Patient_IdOrderByEncounter_EncounterDateDescCreatedAtDesc(Long patientId);
}
