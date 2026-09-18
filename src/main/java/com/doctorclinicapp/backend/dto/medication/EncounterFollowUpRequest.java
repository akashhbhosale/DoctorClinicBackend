package com.doctorclinicapp.backend.dto.medication;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EncounterFollowUpRequest {
    private LocalDate followUpDate;
    private String followUpInstructions;
}
