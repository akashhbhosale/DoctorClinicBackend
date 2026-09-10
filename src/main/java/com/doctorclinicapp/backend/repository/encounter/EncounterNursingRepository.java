package com.doctorclinicapp.backend.repository.encounter;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.doctorclinicapp.backend.model.encounter.EncounterNursing;

public interface EncounterNursingRepository
        extends JpaRepository<EncounterNursing, Long> {

    List<EncounterNursing> findByEncounterId(Long encounterId);

    void deleteByEncounterId(Long encounterId);

    // All nursing records for a patient, across every encounter — used by
    // the Nursing History page (same idea as Encounter History, but for
    // nursing records specifically). Most recent encounter first.
    List<EncounterNursing> findByEncounter_Patient_IdOrderByEncounter_EncounterDateDesc(Long patientId);
}
