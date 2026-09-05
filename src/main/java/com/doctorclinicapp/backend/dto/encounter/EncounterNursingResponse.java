package com.doctorclinicapp.backend.dto.encounter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EncounterNursingResponse {

    private Long id;

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
