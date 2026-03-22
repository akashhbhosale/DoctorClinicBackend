package com.doctorclinicapp.backend.dto.encounter;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import com.doctorclinicapp.backend.enums.DiagnosisType;

@Data
public class AddEncounterDiagnosisRequest {

	@NotNull(message = "Encounter ID is required")
	private Long encounterId;

	@NotNull(message = "ICD Code ID is required")
	private Long icdCodeId;

	@NotNull(message = "Diagnosis type is required")
	private DiagnosisType diagnosisType;
}