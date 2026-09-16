package com.doctorclinicapp.backend.dto.assessment;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddEncounterRadiologyRequest {

    @NotNull(message = "Encounter id is required")
    private Long encounterId;

    @NotNull(message = "Radiology master id is required")
    private Long radiologyMasterId;

    private String radiologyResult;
}
