package com.doctorclinicapp.backend.dto.assessment;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EncounterLaboratoryResponse {

    private Long id;
    private Long encounterId;

    private Long laboratoryMasterId;
    private String testName;

    private String labResult;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<EncounterLaboratoryFileResponse> files;
}