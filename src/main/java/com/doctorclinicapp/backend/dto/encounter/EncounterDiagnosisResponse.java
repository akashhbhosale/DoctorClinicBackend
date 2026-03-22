package com.doctorclinicapp.backend.dto.encounter;

import com.doctorclinicapp.backend.enums.DiagnosisType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EncounterDiagnosisResponse {
	
	private Long id; 

	private DiagnosisType diagnosisType;
    private String code;
    private String description;
}