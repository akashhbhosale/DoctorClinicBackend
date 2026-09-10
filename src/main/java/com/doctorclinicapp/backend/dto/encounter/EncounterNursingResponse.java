package com.doctorclinicapp.backend.dto.encounter;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EncounterNursingResponse {

    private Long id;

    // Included so the Nursing History page (across all encounters for a
    // patient) can show which visit each record belongs to.
    private Long encounterId;
    private LocalDateTime encounterDate;

    private Long assessmentId;
    private String assessment;

    private Long diagnosisId;
    private String diagnosis;

    private Long outcomeId;
    private String outcome;
    private Integer outcomeScore;

    private Long interventionId;
    private String intervention;
}
