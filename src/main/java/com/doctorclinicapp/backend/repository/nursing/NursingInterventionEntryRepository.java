package com.doctorclinicapp.backend.repository.nursing;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.nursing.NursingInterventionEntry;

public interface NursingInterventionEntryRepository extends JpaRepository<NursingInterventionEntry, Long> {

    List<NursingInterventionEntry> findByAssessmentRecordIdOrderByInterventionTimeAsc(Long assessmentRecordId);
}
