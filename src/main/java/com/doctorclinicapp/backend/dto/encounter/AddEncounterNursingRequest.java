package com.doctorclinicapp.backend.dto.encounter;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddEncounterNursingRequest {

    @NotNull(message = "Encounter ID is required")
    private Long encounterId;

    @NotNull(message = "Assessment ID is required")
    private Long assessmentId;

    @NotNull(message = "Diagnosis ID is required")
    private Long diagnosisId;

    // Optional
    private Long outcomeId;
    private Integer outcomeScore;

    @NotNull(message = "Intervention ID is required")
    private Long interventionId;
}
