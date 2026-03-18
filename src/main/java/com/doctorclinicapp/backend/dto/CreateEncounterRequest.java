package com.doctorclinicapp.backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateEncounterRequest {

    @NotNull
    private Long patientId;

    private String notes;

    @Valid
    @Builder.Default
    private List<ComplaintItem> complaints = new ArrayList<>();

    @Valid
    @Builder.Default
    private List<DiagnosisItem> diagnoses = new ArrayList<>();

    @Valid
    @Builder.Default
    private List<ProcedureItem> procedures = new ArrayList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ComplaintItem {
        private String complaint;
        private Integer days;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DiagnosisItem {
        private String diagnosisType;
        private String diagnosis;
        private String icd10;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProcedureItem {
        private String procedure;
        private String laterality;
        private String priority;
        private String site;
        private String device;
        private String method;
    }
}
