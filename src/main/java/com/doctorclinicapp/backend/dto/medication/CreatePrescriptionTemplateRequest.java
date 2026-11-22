package com.doctorclinicapp.backend.dto.medication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

@Data
public class CreatePrescriptionTemplateRequest {

	@NotBlank(message = "Template name is required")
	@Size(max = 100, message = "Template name cannot exceed 100 characters")
	private String templateName;

	@Size(max = 500, message = "Description cannot exceed 500 characters")
	private String description;

	@NotEmpty(message = "At least one medicine is required")
	@Valid
	private List<AddPrescriptionTemplateMedicineRequest> medicines;
}