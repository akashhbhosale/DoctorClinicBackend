package com.doctorclinicapp.backend.dto.nursing;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NursingAssessmentRecordResponse {

    private Long id;
    private Long encounterId;
    private LocalDateTime encounterDate; // used by the history page to group by visit

    private Long assessmentId;
    private String assessment;

    private Long diagnosisId;
    private String diagnosis;

    private Long outcomeId;
    private String outcome;
    private Integer outcomeScore;

    private LocalDateTime createdAt;

    private List<NursingInterventionEntryResponse> interventions;
}
