package com.doctorclinicapp.backend.dto.medication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddEncounterMedicationRequest {

    @NotNull(message = "Encounter id is required")
    private Long encounterId;

    @NotNull(message = "Medicine is required")
    private Long medicineMasterId;

    private Long doseFormId;
    private Long routeOfAdministrationId;

    @NotNull(message = "Direction of use is required")
    private Long directionOfUseId;

    @NotBlank(message = "Dosage is required")
    private String dosage;

    @NotNull(message = "Duration is required")
    private Integer durationDays;

    private String comment;
}
