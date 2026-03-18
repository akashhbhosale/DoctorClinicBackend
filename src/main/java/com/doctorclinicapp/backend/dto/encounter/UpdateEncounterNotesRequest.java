package com.doctorclinicapp.backend.dto.encounter;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateEncounterNotesRequest {

    @NotBlank
    private String notes;

}