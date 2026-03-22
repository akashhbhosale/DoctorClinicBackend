package com.doctorclinicapp.backend.dto.encounter;

import com.doctorclinicapp.backend.enums.Laterality;
import com.doctorclinicapp.backend.enums.Priority;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddEncounterProcedureRequest {

	@NotNull(message = "Encounter ID is required")
	private Long encounterId;

	@NotNull(message = "Procedure ID is required")
	private Long procedureId;

	private Long siteId;
	private Long deviceId;
	private Long methodId;

	private Laterality laterality;
	private Priority priority;
}