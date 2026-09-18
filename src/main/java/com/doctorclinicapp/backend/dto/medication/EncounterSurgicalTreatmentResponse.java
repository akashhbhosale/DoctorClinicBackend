package com.doctorclinicapp.backend.dto.medication;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EncounterSurgicalTreatmentResponse {

    private Long id;
    private Long encounterId;

    private Long surgeryMasterId;
    private String surgeryName;

    private String laterality;
    private String site;
    private String method;
    private String device;
}
