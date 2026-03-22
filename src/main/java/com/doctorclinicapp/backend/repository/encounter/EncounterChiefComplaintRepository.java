package com.doctorclinicapp.backend.repository.encounter;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.doctorclinicapp.backend.model.encounter.EncounterChiefComplaint;

public interface EncounterChiefComplaintRepository
        extends JpaRepository<EncounterChiefComplaint, Long> {

    List<EncounterChiefComplaint> findByEncounterId(Long encounterId);

	boolean existsByEncounterIdAndChiefComplaintId(Long encounterId, Long complaintId);
	
	void deleteByEncounterId(Long encounterId);
    
}