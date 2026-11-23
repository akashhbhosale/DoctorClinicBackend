package com.doctorclinicapp.backend.repository.nursing;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.doctorclinicapp.backend.model.nursing.NursingAssessmentRecord;

public interface NursingAssessmentRecordRepository extends JpaRepository<NursingAssessmentRecord, Long> {

	List<NursingAssessmentRecord> findByEncounterIdOrderByCreatedAtDesc(Long encounterId);

	@Query("select e from NursingAssessmentRecord e where e.encounter.patient.id = ?1 and e.encounter.id not in (select a.encounterId from EncounterArchive a) order by e.encounter.encounterDate desc, e.createdAt desc")
	List<NursingAssessmentRecord> findByEncounter_Patient_IdOrderByEncounter_EncounterDateDescCreatedAtDesc(
			Long patientId);
}
