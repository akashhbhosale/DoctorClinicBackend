package com.doctorclinicapp.backend.dto.assessment;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddEncounterObservationRequest {

    @NotNull(message = "Encounter id is required")
    private Long encounterId;

    @NotNull(message = "Observation master id is required")
    private Long observationMasterId;

    private String assessmentResult;
}
