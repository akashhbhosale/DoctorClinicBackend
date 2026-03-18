package com.doctorclinicapp.backend.dto.encounter;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddEncounterChiefComplaintRequest {

    @NotNull
    private Long encounterId;

    @NotNull
    private Long complaintId;

    private Integer timeSinceDays;
}