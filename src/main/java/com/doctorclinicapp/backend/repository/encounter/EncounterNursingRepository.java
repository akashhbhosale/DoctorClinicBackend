package com.doctorclinicapp.backend.repository.encounter;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.doctorclinicapp.backend.model.encounter.EncounterNursing;

public interface EncounterNursingRepository
        extends JpaRepository<EncounterNursing, Long> {

    List<EncounterNursing> findByEncounterId(Long encounterId);

    void deleteByEncounterId(Long encounterId);
}
