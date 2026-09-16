package com.doctorclinicapp.backend.service.assessment;

import java.util.List;

import com.doctorclinicapp.backend.dto.assessment.AddEncounterObservationRequest;
import com.doctorclinicapp.backend.dto.assessment.EncounterObservationResponse;

public interface EncounterObservationService {

    EncounterObservationResponse addObservation(AddEncounterObservationRequest request);

    List<EncounterObservationResponse> getObservationsByEncounterId(Long encounterId);

    void deleteObservation(Long observationId);
}
