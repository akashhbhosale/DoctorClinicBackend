package com.doctorclinicapp.backend.dto.medication;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineMasterDTO {
    private Long id;
    private String medicineName;
    private String medicineType;
}
