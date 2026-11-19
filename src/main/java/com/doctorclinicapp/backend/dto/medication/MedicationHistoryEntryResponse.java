package com.doctorclinicapp.backend.dto.medication;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * One encounter's worth of medication data, bundled together — what
 * /api/medication-history/patient/{patientId} returns a list of.
 * followUp is nullable (an encounter may never have had one saved);
 * the other two are lists since an encounter can have several of each.
 */
@Data
@Builder
public class MedicationHistoryEntryResponse {

    private Long encounterId;
    private LocalDateTime encounterDate;

    private List<EncounterMedicationResponse> medications;
    private List<EncounterSurgicalTreatmentResponse> surgicalTreatments;
    private EncounterFollowUpResponse followUp; // null if none saved
}
