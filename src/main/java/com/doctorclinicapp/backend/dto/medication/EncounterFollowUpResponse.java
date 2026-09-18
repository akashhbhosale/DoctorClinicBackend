package com.doctorclinicapp.backend.dto.medication;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EncounterFollowUpResponse {
    private Long id;
    private Long encounterId;
    private LocalDate followUpDate;
    private String followUpInstructions;
}
