package com.doctorclinicapp.backend.dto.assessment;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddEncounterLaboratoryRequest {

    @NotNull(message = "Encounter id is required")
    private Long encounterId;

    @NotNull(message = "Laboratory master id is required")
    private Long laboratoryMasterId;

    private String labResult;
}