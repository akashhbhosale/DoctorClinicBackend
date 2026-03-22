package com.doctorclinicapp.backend.repository.encounter;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.doctorclinicapp.backend.model.encounter.EncounterDiagnosis;

public interface EncounterDiagnosisRepository extends JpaRepository<EncounterDiagnosis, Long> {

	List<EncounterDiagnosis> findByEncounter_Id(Long encounterId);

	void deleteByEncounter_Id(Long encounterId);

	boolean existsByEncounterIdAndIcdCodeId(Long encounterId, Long icdCodeId);
}