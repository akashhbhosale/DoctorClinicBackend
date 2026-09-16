package com.doctorclinicapp.backend.dto.assessment;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EncounterRadiologyResponse {

    private Long id;
    private Long encounterId;

    private Long radiologyMasterId;
    private String orderName;

    private String radiologyResult;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<EncounterRadiologyFileResponse> files;
}
