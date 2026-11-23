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

    @org.springframework.data.jpa.repository.Query("select e from EncounterNursing e where e.encounter.patient.id = ?1 and e.encounter.id not in (select a.encounterId from EncounterArchive a) order by e.encounter.encounterDate desc")    List<EncounterNursing> findByEncounter_Patient_IdOrderByEncounter_EncounterDateDesc(Long patientId);
}
