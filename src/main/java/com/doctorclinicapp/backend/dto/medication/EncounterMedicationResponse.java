package com.doctorclinicapp.backend.dto.medication;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EncounterMedicationResponse {

    private Long id;
    private Long encounterId;

    private Long medicineMasterId;
    private String medicineName;
    private String medicineType;

    private Long doseFormId;
    private String doseForm;

    private Long routeOfAdministrationId;
    private String routeOfAdministration;

    private Long directionOfUseId;
    private String directionOfUse;

    private String dosage;
    private Integer durationDays;
    private String comment;
}
