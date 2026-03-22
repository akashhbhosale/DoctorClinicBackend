package com.doctorclinicapp.backend.repository.encounter;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.doctorclinicapp.backend.model.encounter.EncounterProcedure;

public interface EncounterProcedureRepository 
        extends JpaRepository<EncounterProcedure, Long> {

    List<EncounterProcedure> findByEncounterId(Long encounterId);

    void deleteByEncounterId(Long encounterId);
}