package com.doctorclinicapp.backend.dto.medication;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoseFormMasterDTO {
    private Long id;
    private String doseFormName;
}
