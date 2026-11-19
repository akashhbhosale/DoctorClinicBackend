package com.doctorclinicapp.backend.dto.nursing;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NursingInterventionEntryResponse {

    private Long id;
    private Long assessmentRecordId;

    private Long interventionId;
    private String intervention;

    private LocalDateTime interventionTime;
    private String note;
}
