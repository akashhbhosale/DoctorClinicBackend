package com.doctorclinicapp.backend.dto.nursing;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddNursingAssessmentRecordRequest {

    @NotNull(message = "Encounter id is required")
    private Long encounterId;

    @NotNull(message = "Assessment is required")
    private Long assessmentId;

    @NotNull(message = "Diagnosis is required")
    private Long diagnosisId;

    private Long outcomeId;       // optional
    private Integer outcomeScore; // optional
}
