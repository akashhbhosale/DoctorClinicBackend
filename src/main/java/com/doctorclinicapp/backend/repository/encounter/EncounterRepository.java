package com.doctorclinicapp.backend.repository.encounter;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.doctorclinicapp.backend.model.encounter.Encounter;

public interface EncounterRepository extends JpaRepository<Encounter, Long> {

	@Query("select e from Encounter e where e.patient.id = ?1 and e.id not in (select a.encounterId from EncounterArchive a) order by e.encounterDate desc")
	List<Encounter> findByPatientIdOrderByEncounterDateDesc(Long patientId);
}