
package com.doctorclinicapp.backend.dto.medication;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddPrescriptionTemplateMedicineRequest {

    @NotNull(message = "Medicine is required")
    private Long medicineMasterId;

    private Long doseFormId;

    private Long routeOfAdministrationId;

    @NotNull(message = "Direction of use is required")
    private Long directionOfUseId;

    @Size(max = 100, message = "Strength cannot exceed 100 characters")
    private String strength;

    @NotBlank(message = "Dosage is required")
    @Size(max = 100, message = "Dosage cannot exceed 100 characters")
    private String dosage;

    @NotNull(message = "Duration is required")
    private Integer durationDays;

    @DecimalMin(value = "0.01", message = "Quantity must be greater than zero")
    private BigDecimal quantity;

    @Size(max = 50, message = "Quantity unit cannot exceed 50 characters")
    private String quantityUnit;

    @Size(max = 500, message = "Comment cannot exceed 500 characters")
    private String comment;
}
