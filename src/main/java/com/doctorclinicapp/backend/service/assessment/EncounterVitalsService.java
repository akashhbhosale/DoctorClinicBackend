package com.doctorclinicapp.backend.service.assessment;

import com.doctorclinicapp.backend.dto.assessment.EncounterVitalsResponse;
import com.doctorclinicapp.backend.dto.assessment.SaveEncounterVitalsRequest;

public interface EncounterVitalsService {

    EncounterVitalsResponse saveOrUpdateVitals(Long encounterId, SaveEncounterVitalsRequest request);

    EncounterVitalsResponse getVitalsByEncounterId(Long encounterId);

    void deleteVitalsByEncounterId(Long encounterId);
}