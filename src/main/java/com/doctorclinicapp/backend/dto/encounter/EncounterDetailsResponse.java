package com.doctorclinicapp.backend.dto.encounter;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EncounterDetailsResponse {

    private Long encounterId;

    private Long patientId;
    private String patientName;

    private Long doctorId;
    private String doctorName;

    private LocalDateTime encounterDate;

    private String notes;

    private List<EncounterChiefComplaintResponse> complaints;
    private List<EncounterDiagnosisResponse> diagnoses;
    private List<EncounterProcedureResponse> procedures;
}