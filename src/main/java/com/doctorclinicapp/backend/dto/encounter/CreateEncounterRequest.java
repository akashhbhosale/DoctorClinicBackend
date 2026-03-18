package com.doctorclinicapp.backend.dto.encounter;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateEncounterRequest {

    @NotNull
    private Long patientId;

    @NotNull
    private Long doctorId;

}