package com.doctorclinicapp.backend.repository.encounter;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.doctorclinicapp.backend.model.encounter.Encounter;

public interface EncounterRepository extends JpaRepository<Encounter, Long> {
	
	List <Encounter> findByPatientIdOrderByEncounterDateDesc(Long patientId);

}