
package com.doctorclinicapp.backend.dto.medication;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrescriptionTemplateMedicineResponse {

    private Long id;

    private Long medicineMasterId;
    private String medicineName;
    private String medicineType;

    private Long doseFormId;
    private String doseForm;

    private Long routeOfAdministrationId;
    private String routeOfAdministration;

    private Long directionOfUseId;
    private String directionOfUse;

    private String strength;
    private String dosage;
    private Integer durationDays;

    private BigDecimal quantity;
    private String quantityUnit;

    private String comment;
}
