package com.doctorclinicapp.backend.dto.nursing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NursingAssessmentResponse {
    private Long id;
    private String name;
}
