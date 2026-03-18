package com.doctorclinicapp.backend.dto.encounter;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EncounterChiefComplaintResponse {

    private String complaint;
    private Integer timeSinceDays;
}