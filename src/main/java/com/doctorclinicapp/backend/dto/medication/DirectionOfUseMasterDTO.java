package com.doctorclinicapp.backend.dto.medication;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DirectionOfUseMasterDTO {
    private Long id;
    private String directionName;
}
