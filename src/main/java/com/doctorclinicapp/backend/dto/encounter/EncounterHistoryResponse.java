package com.doctorclinicapp.backend.dto.encounter;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EncounterHistoryResponse {
    private Long id;
    private LocalDateTime encounterDate;
    private Long doctorId;
    private String doctorName;
    private String notes;
}