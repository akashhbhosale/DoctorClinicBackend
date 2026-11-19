package com.doctorclinicapp.backend.dto.nursing;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddNursingInterventionRequest {

    @NotNull(message = "Assessment record id is required")
    private Long assessmentRecordId;

    @NotNull(message = "Intervention is required")
    private Long interventionId;

    // Optional — if the nurse doesn't set a specific time, the server
    // timestamps it as "now" (when they clicked Add).
    private LocalDateTime interventionTime;

    private String note;
}
