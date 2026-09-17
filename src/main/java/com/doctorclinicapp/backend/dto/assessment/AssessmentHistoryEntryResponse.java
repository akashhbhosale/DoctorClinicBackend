package com.doctorclinicapp.backend.dto.assessment;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * One encounter's worth of assessment data, bundled together — this is
 * what /api/assessment/history/patient/{patientId} returns a list of.
 * Vitals is nullable (an encounter may never have had vitals saved);
 * the other three are lists since an encounter can have several of each.
 */
@Data
@Builder
public class AssessmentHistoryEntryResponse {

    private Long encounterId;
    private LocalDateTime encounterDate;

    private EncounterVitalsResponse vitals; // null if none recorded

    private List<EncounterLaboratoryResponse> laboratory;
    private List<EncounterRadiologyResponse> radiology;
    private List<EncounterObservationResponse> observations;
}
