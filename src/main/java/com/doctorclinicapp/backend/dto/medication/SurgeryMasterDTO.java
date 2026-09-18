package com.doctorclinicapp.backend.dto.medication;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurgeryMasterDTO {
    private Long id;
    private String surgeryName;
}
