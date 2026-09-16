package com.doctorclinicapp.backend.dto.assessment;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EncounterObservationResponse {

    private Long id;
    private Long encounterId;

    private Long observationMasterId;
    private String observationName;

    private String assessmentResult;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
