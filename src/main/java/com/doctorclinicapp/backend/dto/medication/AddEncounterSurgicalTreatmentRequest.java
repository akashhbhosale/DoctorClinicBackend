package com.doctorclinicapp.backend.dto.medication;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddEncounterSurgicalTreatmentRequest {

    @NotNull(message = "Encounter id is required")
    private Long encounterId;

    @NotNull(message = "Surgery is required")
    private Long surgeryMasterId;

    private String laterality; // "LEFT" | "RIGHT" | "BILATERAL", optional

    private String site;
    private String method;
    private String device;
}
